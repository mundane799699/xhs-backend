package com.mundane.mail.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mail.xhs")
public class XhsConfig {

    private String resPath;

    private String imgFolderPath;
}
