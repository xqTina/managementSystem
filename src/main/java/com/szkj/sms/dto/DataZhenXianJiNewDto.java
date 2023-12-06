package com.szkj.sms.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/9/23 21:51
 */
@Data
public class DataZhenXianJiNewDto {

    private Integer id;

    /**
     * 设备序列号ID
     */
    private Integer deviceId;

    /**
     * 设备序列号
     */
    private String deviceDeviceId;


    /**
     * 当前dtu是否在线
     */
    private Integer isOnline;

    /**
     * 当前dtu地址
     */
    private String address;

    /**
     * 表格属性 必须添加 表示未dtuDTuId
     */
    private String name;

    /**
     * 表格属性 正弦表Id 实时更新
     */
    private Integer ZhenXianId = 0;

//    private Integer parentId;

    private List<DataZhenXianJiNewDto> children;


}
