package com.mundane.mail.service;

import com.mundane.mail.config.MailUserConfig;
import com.mundane.mail.pojo.MailUser;
import com.mundane.mail.pojo.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@EnableConfigurationProperties(MailUserConfig.class)
public class ScheduleService {

    @Autowired
    private MailService mailService;

    @Autowired
    private MailUserConfig mailUserConfig;

    public void sendPaySuccess(String totalFee) {
        String text = String.format("恭喜，您有新的订单了！支付金额%s元。", totalFee);
        String subject = "新订单通知！！！";
        mailService.sendSimpleMail(mailUserConfig.getList(), subject, text);
    }

    public void sendInstallSuccess(List<MailUser> list) {
        String text = "好消息，有人安装了你的插件";
        String subject = "插件安装通知";
        mailService.sendSimpleMail(list, subject, text);
    }

}
