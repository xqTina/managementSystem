package com.szkj.sms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/24 11:17
 */
@Data
public class UserDeviceDto {

    /**
     * device_user.id
     */
    private Integer id;

    /**
     * 设备序列号
     */
    private String deviceDeviceId;

    /**
     * DTU序列号
     */
    private String dtuDtuId;

    /**
     * device.id
     */
    private Integer deviceId;

    /**
     * 设备通道数
     */
    private Integer numberOfChannels;


    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 绑定时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bindTime;

    /**
     * 设备备注
     */
    private String remark;
}
