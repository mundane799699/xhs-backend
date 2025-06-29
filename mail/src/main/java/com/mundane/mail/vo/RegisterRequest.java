package com.mundane.mail.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String userName;
    private String password;
    private String email;

    private String clientId;
    private String userId;
}
