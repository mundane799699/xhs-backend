package com.mundane.mail.pojo;

import lombok.Data;

@Data
public class WxLoginRequest {
    private String code;

    private String data;

    private String iv;
}
