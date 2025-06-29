package com.mundane.mail.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.mundane.mail.pojo.Response;
import com.mundane.mail.service.TestService;
import com.zaxxer.hikari.HikariConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/mail/test")
public class TestController {


    @PostMapping("/check")
    public Response init() {
        StpUtil.checkLogin();
        return Response.success(StpUtil.getLoginIdAsLong());
    }
}
