package com.mundane.mail.service;

import com.mundane.mail.vo.WeChatVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class WeChatService {

    @Autowired
    private RestTemplate restTemplate;

    public void sendNewsListToGroup(List<String> newsList) {
        WeChatVO weChatVO = new WeChatVO();
        weChatVO.setMsg(newsList);
        String url = "http://localhost:8089/hello";
        String response = restTemplate.postForObject(url, weChatVO, String.class);
        log.info("hello接口返回: {}", response);
    }


}
