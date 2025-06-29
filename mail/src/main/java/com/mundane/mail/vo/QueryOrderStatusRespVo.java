package com.mundane.mail.vo;


import com.mundane.mail.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryOrderStatusRespVo {

    private boolean active;

    private UserInfo userInfo;
}
