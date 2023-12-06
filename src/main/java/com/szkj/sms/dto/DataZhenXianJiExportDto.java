package com.szkj.sms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 21:51
 */
@Data
public class DataZhenXianJiExportDto {


    private Integer id;

    /**
     * 设备表id
     */
    private String deviceId;

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
    private String channelId;

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
    private String temperature;

    /**
     * 频率
     */
    private String freqency;


    private String yingbian;

    private String data3;
    private String data4;
    private String data5;
    private String data6;
    private String data7;
    private String data8;
    private String data9;
    private String data10;
    private String data11;
    private String data12;
    private String data13;
    private String data14;
    private String data15;

    /**
     * 数据1最大值
     */
    private String maxAlarmValue1;

    /**
     * 数据1最小值
     */
    private String minAlarmValue1;

    /**
     * 数据2最大值
     */
    private String maxAlarmValue2;

    /**
     * 数据2最小值
     */
    private String minAlarmValue2;

    /**
     * 数据3最大值
     */
    private String maxAlarmValue3;

    /**
     * 数据3最小值
     */
    private String minAlarmValue3;
}
