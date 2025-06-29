package com.mundane.mail.controller;

import com.mundane.mail.pojo.MailUser;
import com.mundane.mail.pojo.Response;
import com.mundane.mail.service.ProxyService;
import com.mundane.mail.vo.ClientStatusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mail/proxy")
public class ProxyController {

    @Autowired
    private ProxyService proxyService;


    @GetMapping("/getProxy")
    public Response<String> getProxy() {
        String proxy = proxyService.getProxy();
        return Response.success(proxy);
    }
}
