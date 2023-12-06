package com.szkj.sms.vo;

import lombok.Data;

/**
 * 发送警告短信实体类
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/10/7 11:59
 */
@Data
public class SendWarningVo {
    /**
     * 接收人手机号
     */
    private String phoneNum;

    /**
     * 异常DTU序列号
     */
    private String dtu;

    /**
     * 异常采集设备序列号
     */
    private String device;

    /**
     * 异常通道编号
     */
    private String noc;

    /**
     * 异常数据
     */
    private String data;

    /**
     * 超出值
     */
    private String beyond;

    /**
     * 异常数据采集时间
     */
    private String time;

    /**
     * 校验ID
     */
    private String id;
}
