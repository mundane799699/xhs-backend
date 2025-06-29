package com.mundane.mail.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class ProxyService {
    public String getProxy() {
        // API URL
        String apiUrl = "https://dps.kdlapi.com/api/getdps";

        // 请求参数
        String secretId = "obmv1ozoiwxvozlcg0gc";
        String signature = "hiyy6mnl1nfrcd90yf9zkvxy880i7mek";
        int num = 1;

        // 使用UriComponentsBuilder构建URL
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("secret_id", secretId)
                .queryParam("signature", signature)
                .queryParam("num", num);

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        // 创建HttpEntity，这里我们不需要请求体，所以使用HttpEntity<String>
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 创建RestTemplate实例
        RestTemplate restTemplate = new RestTemplate();

        // 发送GET请求
        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

        // 打印响应内容
        System.out.println(response.getBody());
        return response.getBody();
    }
}
