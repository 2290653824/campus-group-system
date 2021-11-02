package com.swpu.utils;

import com.swpu.vo.MailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@Slf4j
public class MailUtils {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Async
    public String sendSimpleMail(MailVo mailVo) {
        try{
            if (mailVo.isHtml()){
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom(mailProperties.getUsername());
                messageHelper.setTo(mailVo.getRecivers());
                messageHelper.setSubject(mailVo.getSubject());
                messageHelper.setText(mailVo.getContent(),true);
                javaMailSender.send(mimeMessage);
//                log.info("HTML格式邮件发送成功！收件人---{}---", Arrays.asList(mailVo.getRecivers()));
            }else {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(mailProperties.getUsername());
                message.setTo(mailVo.getRecivers());
                message.setSubject(mailVo.getSubject());
                message.setText(mailVo.getContent());
                javaMailSender.send(message);
//                log.info("邮件发送成功！收件人---{}---", Arrays.asList(mailVo.getRecivers()));
            }
            return "邮件发送成功";
        }catch (Exception e){
            e.printStackTrace();
            return "邮件发送失败";
        }
    }

}
