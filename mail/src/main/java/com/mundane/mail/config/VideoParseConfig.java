package com.mundane.mail.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mail.video")
public class VideoParseConfig {
    private String clientSecretKey;
    private String clientId;
}
