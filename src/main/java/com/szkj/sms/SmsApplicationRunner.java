package com.szkj.sms;

import com.szkj.sms.config.SystemConfig;
import com.szkj.sms.util.MailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
*
* @author <a href="http://sixiwenwu.com"/>
* @date 2021/11/27
*/
@Component
public class SmsApplicationRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(SmsApplicationRunner.class);

    @Autowired
    MailUtil mailUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("系统名称：" + SystemConfig.systemName);
        logger.info("系统版本：" + SystemConfig.version);
        logger.info("项目启动完成");
        logger.info("正在从数据库同步Email电子邮件设置...");
        JavaMailSenderImpl jms = mailUtil.configEmail(null);
        if (jms==null){
            logger.info("数据库中的Email设置不完整，保留项目原有设置");
        }else{
            logger.info("同步成功，当前发件人："+jms.getUsername());
        }
    }
}
