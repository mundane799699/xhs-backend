package com.mundane.mail.vo;

import com.mundane.mail.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRespVo {

    private boolean hasLogin;

    private UserInfo userInfo;
}
