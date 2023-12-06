package com.szkj.sms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.Date;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 21:51
 */
@Data
public class DataZhenXianJiDto {

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
     * 频率
     */
    private Float freqency;


    private Double yingbian;

    private Double data3;
    private Double data4;
    private Double data5;
    private Double data6;
    private Double data7;
    private Double data8;
    private Double data9;
    private Double data10;
    private Double data11;
    private Double data12;
    private Double data13;
    private Double data14;
    private Double data15;

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
