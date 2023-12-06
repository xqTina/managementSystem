package com.szkj.sms.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 系统信息配置类
 *
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/27 21:22
 */
@Configuration
@Data
@AllArgsConstructor
public class SystemConfig {

    /**
     * 系统名称
     */
    public static String systemName;

    /**
     * 系统简称
     */
    public static String nickName;

    /**
     * 系统版本号
     */
    public static String version;

    /**
     * 系统名称
     *
     * @param name 名称
     */
    @Value("${system.systemName}")
    public void setSystemName(String name) {
        systemName = name;
    }

    /**
     * 系统简称
     *
     * @param name 名称
     */
    @Value("${system.nickName}")
    public void setNickName(String name) {
        nickName = name;
    }

    /**
     * 系统版本号
     *
     * @param ver 版本号
     */
    @Value("${system.version}")
    public void setVersion(String ver) {
        version = ver;
    }

}
