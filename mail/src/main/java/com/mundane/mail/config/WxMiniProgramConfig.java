package com.mundane.mail.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mail.wx")
public class WxMiniProgramConfig {

    private String appId;

    private String secret;
}
