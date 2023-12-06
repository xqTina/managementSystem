package com.szkj.sms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/28 13:19
 */
@Data
public class DeviceBindUserDto {
    /**
     * device_user.id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 设备ID
     */
    private Integer deviceId;

    /**
     * 绑定时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bindTime;
}
