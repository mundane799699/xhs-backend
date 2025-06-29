package com.mundane.mail.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.mundane.mail.dto.UserInfo;
import com.mundane.mail.entity.RedArchiveUser;

public class CommonUtil {

    public static UserInfo getUserInfo(RedArchiveUser user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(user.getUserName());
        userInfo.setMembershipExpiry(user.getMembershipExpiry());
        userInfo.setMembershipType(user.getMembershipType());
        userInfo.setEmail(user.getEmail());
        userInfo.setToken(StpUtil.getTokenValue());
        return userInfo;
    }
}
