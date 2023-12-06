package com.szkj.sms.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/10/3 14:57
 */
@Configuration
public class DruidConfig {

    /**
     * 解决druid 日志报错：discard long time none received connection:xxx
     */
    @PostConstruct
    public void setProperties() {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }
}
