package com.szkj.sms.util;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.szkj.sms.config.PostMailConfig;
import com.szkj.sms.entity.SysSettings;
import com.szkj.sms.service.impl.SysSettingsServiceImpl;
import com.szkj.sms.vo.SendMailVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 邮件工具类
 *
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/10/7 9:31
 */
@Component
public class MailUtil {

    private final Logger logger = LoggerFactory.getLogger(MailUtil.class);
    private final String ID = "csszkj";
    private JavaMailSenderImpl javaMailSender;
    private final SysSettingsServiceImpl sysSettingsService;

    @Autowired
    public MailUtil(JavaMailSenderImpl javaMailSender, SysSettingsServiceImpl sysSettingsService) {
        this.javaMailSender = javaMailSender;
        this.sysSettingsService = sysSettingsService;
    }

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Value("${spring.mail.nickname}")
    private String nickname;

    /**
     * 发送简单邮件
     *
     * @param sendMailVo 发送对象
     * @return JsonResult
     */
    public JsonResult simpleMail(SendMailVo sendMailVo) {
        if (sendMailVo == null || !ID.equals(sendMailVo.getId()) || StringUtils.isAnyBlank(sendMailVo.getTo(), sendMailVo.getContent(), sendMailVo.getSubject())) {
            return JsonResult.error("参数异常");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(nickname.isEmpty()?"传感器信息数据管理系统":nickname + '<' + mailFrom + '>');
        message.setTo(sendMailVo.getTo());
        message.setSubject("[系统通知]" + sendMailVo.getSubject());
        message.setText(sendMailVo.getContent());
        try {
            logger.info("发送邮件message={}", message.toString());
            javaMailSender.send(message);
        } catch (Exception e) {
            logger.error("发送邮件发生错误e={}", e.getMessage());
            return JsonResult.error("发送邮件发生异常" + e.getMessage());
        }
        logger.info("邮件发送完成");
        return JsonResult.success();
    }

    /**
     * 获取当前邮件配置
     *
     * @return
     */
    public PostMailConfig getEmailConfig() {
        return PostMailConfig.builder()
                .mailHost(javaMailSender.getHost())
                .mailPassword(javaMailSender.getPassword())
                .mailPort(javaMailSender.getPort())
                .mailUsername(javaMailSender.getUsername())
                .mailNickname(nickname)
                .build();
    }

    /**
     * 动态更新发件人配置
     *
     * @param postMailConfig
     * @return
     */
    public JavaMailSenderImpl configEmail(PostMailConfig postMailConfig) {
        if (postMailConfig == null) {
            // 未传入配置，从数据库获取配置
            SysSettings sysSettings = sysSettingsService.getById(1);
            postMailConfig = PostMailConfig.builder()
                    .mailHost(sysSettings.getEmailHost())
                    .mailPort(sysSettings.getEmailPort())
                    .mailUsername(sysSettings.getEmailUsername())
                    .mailPassword(sysSettings.getEmailPassword())
                    .mailNickname(sysSettings.getEmailNickname())
                    .build();
        }
        // 基本配置校验
        if (postMailConfig.getMailPort()<=0||StringUtils.isAnyEmpty(postMailConfig.getMailUsername(),postMailConfig.getMailHost(),postMailConfig.getMailPassword())){
            return null;
        }
        JavaMailSenderImpl jms = new JavaMailSenderImpl();
        jms.setHost(postMailConfig.getMailHost());
        jms.setPort(postMailConfig.getMailPort());
        jms.setUsername(postMailConfig.getMailUsername());
        jms.setPassword(postMailConfig.getMailPassword());
        jms.setDefaultEncoding("UTF-8");
        Properties p = new Properties();
        if (postMailConfig.getMailPort() == 465) {
            p.setProperty("mail.smtp.ssl.enable", "true");
        } else {
            p.setProperty("mail.smtp.ssl.enable", "false");
        }
        p.setProperty("mail.smtp.auth", "true");
        p.setProperty("mail.smtp.starttls.enable", "true");
        p.setProperty("mail.smtp.starttls.required", "true");
        p.setProperty("imap.ssl.socketFactory.fallback", "false");
        jms.setJavaMailProperties(p);
        // 覆盖已注入的JavaMailSenderImpl
        this.javaMailSender = jms;
        // 更新username
        this.mailFrom = javaMailSender.getUsername();
        // 更新nickname
        this.nickname = postMailConfig.getMailNickname();
        // 将数据同步到数据库
        sysSettingsService.update(
                new UpdateWrapper<SysSettings>()
                        .eq("id",1)
                        .set("email_host",jms.getHost())
                        .set("email_port",jms.getPort())
                        .set("email_username",jms.getUsername())
                        .set("email_password",jms.getPassword())
                        .set("email_nickname",postMailConfig.getMailNickname())
        );
        return jms;
    }
}
