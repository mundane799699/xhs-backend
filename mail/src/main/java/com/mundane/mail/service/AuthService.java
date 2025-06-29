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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private RedArchiveUserMapper userMapper;

    @Autowired
    private RedBookClientMapper clientMapper;

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
