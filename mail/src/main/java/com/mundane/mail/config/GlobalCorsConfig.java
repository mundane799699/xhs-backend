package com.mundane.mail.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
@EnableConfigurationProperties(CorsConfig.class)
public class GlobalCorsConfig {

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("https://mundane.ink");
//        config.addAllowedOrigin("http://mundane.ink");
//        config.addAllowedOrigin("http://xiaohongshu.com");
//        config.addAllowedOrigin("https://xiaohongshu.com");
//        config.addAllowedOrigin("http://www.xiaohongshu.com");
//        config.addAllowedOrigin("https://www.xiaohongshu.com");
//        config.addAllowedOrigin("https://xhs.mundane.ink");
//        config.addAllowedOrigin("http://xhs.mundane.ink");
//        config.addAllowedOrigin("https://bilibili.mundane.ink");
//        config.addAllowedOrigin("https://bilibili.com");
//        config.addAllowedOrigin("http://bilibili.com");
//        config.addAllowedOrigin("https://www.bilibili.com");
//        config.addAllowedOrigin("http://www.bilibili.com");
        config.addAllowedOrigin("*");
        config.setAllowCredentials(false);
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        config.addAllowedHeader("*");
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(configSource);
    }
}
