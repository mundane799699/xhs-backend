package com.mundane.mail.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.mundane.mail.constant.CommonConstants;
import com.mundane.mail.entity.RedArchiveUser;
import com.mundane.mail.entity.RedBookActiveCodeEntity;
import com.mundane.mail.entity.RedBookItemEntity;
import com.mundane.mail.mapper.RedArchiveUserMapper;
import com.mundane.mail.mapper.RedBookActiveCodeMapper;
import com.mundane.mail.mapper.RedBookItemMapper;
import com.mundane.mail.utils.ActivationCodeGenerator;
import com.mundane.mail.utils.CommonUtil;
import com.mundane.mail.vo.QueryOrderStatusRespVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RedBookActiveCodeService {

    @Autowired
    private RedBookActiveCodeMapper redBookActiveCodeMapper;


    @Autowired
    private RedBookItemMapper itemMapper;

    @Autowired
    private RedArchiveUserMapper userMapper;

    public void generate() {
        LocalDateTime now = LocalDateTime.now();
        List<RedBookActiveCodeEntity> list = new ArrayList<>();
        for (int i = 0; i < 900; i++) {
            RedBookActiveCodeEntity entity = new RedBookActiveCodeEntity();
            entity.setActiveCode(ActivationCodeGenerator.generateActivationCode());
            entity.setStatus(0);
            entity.setItemType(i % 3 + 1);
            entity.setCreateTime(now);
            entity.setUpdateTime(now);
            list.add(entity);
        }
        redBookActiveCodeMapper.insertList(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public QueryOrderStatusRespVo active(String activeCode) {
        QueryOrderStatusRespVo result = new QueryOrderStatusRespVo();
        LocalDateTime now = LocalDateTime.now();

        RedBookActiveCodeEntity entity = redBookActiveCodeMapper.selectByPrimaryKey(activeCode);
        if (entity == null) {
            result.setActive(false);
            return result;
        }
        if (entity.getStatus() == 2 || entity.getStatus() == 3) {
            result.setActive(false);
            return result;
        }
        entity.setStatus(2);
        entity.setUpdateTime(now);
        redBookActiveCodeMapper.updateByPrimaryKey(entity);
        // 修改用户表
        RedBookItemEntity item = itemMapper.selectByPrimaryKey(entity.getItemType());

        RedArchiveUser user = userMapper.selectByPrimaryKey(StpUtil.getLoginIdAsLong());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setUpdateTime(now);
        if (item.getMembershipType() == CommonConstants.LIFE_TIME) {
            user.setMembershipType(CommonConstants.LIFE_TIME);
            user.setMembershipExpiry(null);
        } else {
            user.setMembershipType(item.getMembershipType());
            LocalDateTime membershipExpiry = user.getMembershipExpiry();
            if (membershipExpiry != null && membershipExpiry.isAfter(now)) {
                user.setMembershipExpiry(membershipExpiry.plusDays(item.getDays()));
            } else {
                user.setMembershipExpiry(now.plusDays(item.getDays()));
            }
        }
        userMapper.updateByPrimaryKey(user);
        result.setActive(true);
        result.setUserInfo(CommonUtil.getUserInfo(user));
        return result;
    }
}
