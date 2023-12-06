package com.szkj.sms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Device实体类
 *
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 17:05
 */
@Data
public class DeviceDto {

    private Integer id;

    /**
     * DTU序列号
     */
    private String dtuDtuId;

    /**
     * 设备序列号
     */
    private String deviceId;

    /**
     * 设备通道数
     */
    private Integer numberOfChannels;

    /**
     * DTU_id
     */
    private Integer dtuId;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 数据1最大值
     */
    private Double maxAlarmValue1;

    /**
     * 数据1最小值
     */
    private Double minAlarmValue1;

    /**
     * 数据2最大值
     */
    private Double maxAlarmValue2;

    /**
     * 数据2最小值
     */
    private Double minAlarmValue2;

    /**
     * 数据3最大值
     */
    private Double maxAlarmValue3;

    /**
     * 数据3最小值
     */
    private Double minAlarmValue3;

    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;

    /**
     * 备注
     */
    private String remark;
}
