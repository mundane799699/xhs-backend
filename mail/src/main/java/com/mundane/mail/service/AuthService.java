package com.mundane.mail.service;

import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.mundane.mail.entity.RedArchiveUser;
import com.mundane.mail.entity.RedBookClientEntity;
import com.mundane.mail.mapper.RedArchiveUserMapper;
import com.mundane.mail.mapper.RedBookClientMapper;
import com.mundane.mail.vo.LoginRequest;
import com.mundane.mail.dto.UserInfo;
import com.mundane.mail.vo.RegisterRequest;
import com.mundane.mail.vo.ResetPasswordRequest;
import com.mundane.mail.vo.SendResetCodeRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private RedArchiveUserMapper userMapper;

    @Autowired
    private RedBookClientMapper clientMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String RESET_CODE_KEY = "reset_pwd:code:";
    private static final String RESET_LIMIT_KEY = "reset_pwd:limit:";
    private static final String RESET_ATTEMPT_KEY = "reset_pwd:attempt:";
    private static final long RESET_CODE_EXPIRE_MINUTES = 10;
    private static final long RESET_SEND_INTERVAL_SECONDS = 60;
    private static final int RESET_MAX_ATTEMPTS = 5;
    private static final SecureRandom RANDOM = new SecureRandom();

    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterRequest vo) {
        RedArchiveUser existUser = userMapper.findByEmail(vo.getEmail());
        if (existUser != null) {
            throw new IllegalArgumentException("该邮箱已被注册");
        }
        RedArchiveUser user = new RedArchiveUser();
        user.setUserName(vo.getUserName());
        user.setEmail(vo.getEmail());
        user.setStatus("1");
        user.setPassword(BCrypt.hashpw(vo.getPassword()));
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        user.setMembershipType("FREE");
        user.setMembershipExpiry(now.plusDays(1));

        // 如果有老账号的信息，则把老账号的信息迁移过来
        if (StringUtils.isNotEmpty(vo.getClientId()) || StringUtils.isNotEmpty(vo.getUserId())) {
            RedBookClientEntity client = clientMapper.selectByClientId(vo.getClientId(), vo.getUserId());
            if (client != null) {
                String endDateStr = client.getEndDate();
                if ("-1".equals(endDateStr)) {
                    user.setMembershipType("LIFETIME");
                    user.setMembershipExpiry(null);
                } else {
                    user.setMembershipType("MIGRATE");
                    LocalDateTime endDate = DateUtil.parseLocalDateTime(endDateStr, "yyyy-MM-dd");
                    user.setMembershipExpiry(endDate);
                }
                client.setStatus(0);
                clientMapper.updateByPrimaryKey(client);
            }
        }
        userMapper.insert(user);
    }

    public UserInfo login(LoginRequest vo) {
        RedArchiveUser user = userMapper.findByEmail(vo.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("该邮箱未注册");
        }
        if (!BCrypt.checkpw(vo.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("密码错误");
        }
        if (!StringUtils.equals("1", user.getStatus())) {
            throw new IllegalArgumentException("该账户已被停用");
        }
        StpUtil.login(user.getUserId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        String token = tokenInfo.getTokenValue();

        UserInfo response = new UserInfo();
        response.setMembershipExpiry(user.getMembershipExpiry());
        response.setEmail(user.getEmail());
        response.setUserName(user.getUserName());
        response.setToken(token);
        response.setMembershipType(user.getMembershipType());
        return response;
    }

    public void logout() {
        // 服务重启的时候，token会失效
        boolean isLogin = StpUtil.isLogin();
        log.info("isLogin = {}", isLogin);
        if (!isLogin) {
            return;
        }
        StpUtil.logout();
    }

    public void sendResetCode(SendResetCodeRequest vo) {
        String email = StringUtils.trim(vo.getEmail());
        if (StringUtils.isEmpty(email)) {
            throw new IllegalArgumentException("邮箱不能为空");
        }
        RedArchiveUser user = userMapper.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("该邮箱未注册");
        }
        // 60 秒内只能发送一次
        Boolean allowed = redisTemplate.opsForValue()
                .setIfAbsent(RESET_LIMIT_KEY + email, "1", RESET_SEND_INTERVAL_SECONDS, TimeUnit.SECONDS);
        if (!Boolean.TRUE.equals(allowed)) {
            throw new IllegalArgumentException("验证码发送过于频繁，请稍后再试");
        }
        String code = String.format("%06d", RANDOM.nextInt(1000000));
        try {
            mailService.sendResetPasswordCode(email, code, RESET_CODE_EXPIRE_MINUTES);
        } catch (Exception e) {
            redisTemplate.delete(RESET_LIMIT_KEY + email);
            log.error("发送重置密码验证码失败, email = {}", email, e);
            throw new IllegalStateException("验证码邮件发送失败，请稍后再试");
        }
        redisTemplate.opsForValue().set(RESET_CODE_KEY + email, code, RESET_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        redisTemplate.delete(RESET_ATTEMPT_KEY + email);
    }

    public void resetPassword(ResetPasswordRequest vo) {
        String email = StringUtils.trim(vo.getEmail());
        if (StringUtils.isAnyEmpty(email, vo.getCode(), vo.getNewPassword())) {
            throw new IllegalArgumentException("邮箱、验证码和新密码不能为空");
        }
        String codeKey = RESET_CODE_KEY + email;
        String attemptKey = RESET_ATTEMPT_KEY + email;
        String savedCode = redisTemplate.opsForValue().get(codeKey);
        if (savedCode == null) {
            throw new IllegalArgumentException("验证码已失效，请重新获取");
        }
        if (!savedCode.equals(StringUtils.trim(vo.getCode()))) {
            // 错误次数过多则作废验证码，防止暴力破解
            Long attempts = redisTemplate.opsForValue().increment(attemptKey);
            redisTemplate.expire(attemptKey, RESET_CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
            if (attempts != null && attempts >= RESET_MAX_ATTEMPTS) {
                redisTemplate.delete(codeKey);
                redisTemplate.delete(attemptKey);
                throw new IllegalArgumentException("验证码错误次数过多，请重新获取");
            }
            throw new IllegalArgumentException("验证码错误");
        }
        RedArchiveUser user = userMapper.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("该邮箱未注册");
        }
        RedArchiveUser update = new RedArchiveUser();
        update.setUserId(user.getUserId());
        update.setPassword(BCrypt.hashpw(vo.getNewPassword()));
        update.setUpdateTime(LocalDateTime.now());
        userMapper.updateByPrimaryKeySelective(update);
        redisTemplate.delete(codeKey);
        redisTemplate.delete(attemptKey);
        // 密码重置后，让该账号已登录的设备全部下线
        StpUtil.logout(user.getUserId());
    }

    public UserInfo getUserInfo() {
        Long userId = StpUtil.getLoginIdAsLong();
        RedArchiveUser user = userMapper.selectByPrimaryKey(userId);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(user.getUserName());
        userInfo.setMembershipExpiry(user.getMembershipExpiry());
        userInfo.setMembershipType(user.getMembershipType());
        userInfo.setEmail(user.getEmail());
        userInfo.setToken(StpUtil.getTokenValue());
        return userInfo;
    }
}
