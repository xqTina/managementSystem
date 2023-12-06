package com.szkj.sms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.Date;

/**
 * 倾斜计数据
 *
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 21:30
 */
@Data
public class DataQingXieJiDto {

    private Integer id;

    /**
     * 设备表id
     */
    private Integer deviceId;

    /**
     * DTU序列号
     */
    private String dtuDtuId;

    /**
     * 设备序列号
     */
    private String deviceDeviceId;

    /**
     * 通道编号
     */
    private Integer channelId;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    /**
     * 时间
     */
    private Time time;

    /**
     * 宽度
     */
    private Float temperature;

    /**
     * 倾斜角度
     */
    private Float angle;

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
}
