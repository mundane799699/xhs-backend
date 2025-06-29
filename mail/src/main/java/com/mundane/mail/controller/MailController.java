package com.mundane.mail.controller;

import com.mundane.mail.pojo.MailUser;
import com.mundane.mail.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mail/sendMail")
public class MailController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/send")
    public String sendWeatherReport() {
        List<MailUser> list = new ArrayList<>();
        MailUser user1 = new MailUser("圆圆", "799699348@qq.com");
        list.add(user1);
        MailUser user2 = new MailUser("张斯克", "759965663@qq.com");
        list.add(user2);
        scheduleService.sendInstallSuccess(list);
        return "success";
    }
}
