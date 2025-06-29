package com.mundane.mail;

import com.mundane.mail.service.NewsService;
import com.mundane.mail.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class NewsServiceTests {

    @Autowired
    private NewsService newsService;

    @Autowired
    private WeChatService weChatService;


    @Test
    public void testGetNewsList() {
        List<String> newsList = newsService.getNewsList();
        log.info("newsList = {}", newsList);
    }
}
