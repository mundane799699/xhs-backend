package com.mundane.mail.service;

import com.mundane.mail.pojo.MailUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMail(List<MailUser> userList, String subject, String text) {
        for (MailUser user : userList) {
            try {
                // 创建简单邮件消息
                SimpleMailMessage message = new SimpleMailMessage();
                // 谁发的
                message.setFrom("mundane799699@163.com");
                // 谁要接收
                message.setTo(user.getMailAddress());
                // 邮件标题
                message.setSubject(subject);
                String newText = String.format("亲爱的%s,\n%s", user.getName(), text);
                // 邮件内容
                message.setText(newText);
                mailSender.send(message);
            } catch (MailException e) {
                e.printStackTrace();
            }
        }
    }

    public void reportPictureUpdated() {
        SimpleMailMessage message = new SimpleMailMessage();
        // 谁发的
        message.setFrom("mundane799699@163.com");
        // 谁要接收
        message.setTo("799699348@qq.com");
        // 邮件标题
        message.setSubject("美女图更新");
        String newText = "亲爱的圆圆，美女图片有更新啦！";
        // 邮件内容
        message.setText(newText);
        mailSender.send(message);
    }
}
