package com.szkj.sms;

import cn.shuibo.annotation.EnableSecurity;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 程序主入口
 *
 * @author sixiwenwu
 * @date 2021/9/20
 */
@MapperScan("com.szkj.sms.mapper")
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@EnableSecurity
public class SmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class, args);
    }

}
