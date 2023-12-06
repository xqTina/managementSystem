package com.szkj.sms.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云短信服务配置项
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/10/7 11:27
 */
@Configuration
@Data
@AllArgsConstructor
public class TencentSmsConfig {

    public static String SECRETID;

    public static String SECRETKEY;

    public static String SMSSDKAPPID;

    public static String SIGNNAME;

    @Value("${tencentsms.secretid}")
    public void setSECRETID(String secretid) {
        SECRETID = secretid;
    }

    @Value("${tencentsms.secretkey}")
    public void setSECRETKEY(String secretkey) {
       SECRETKEY = secretkey;
    }

    @Value("${tencentsms.appid}")
    public void setSMSSDKAPPID(String appid) {
        SMSSDKAPPID = appid;
    }

    @Value("${tencentsms.signname}")
    public void setSIGNNAME(String signname) {
        SIGNNAME = signname;
    }
}
