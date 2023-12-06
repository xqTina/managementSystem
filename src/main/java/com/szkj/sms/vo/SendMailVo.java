package com.szkj.sms.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 发送邮件实体类
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/10/7 9:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendMailVo {

    /**
     * 收件人
     */
    private String to;

    /**
     * 信件标题
     */
    private String subject;

    /**
     * 信件内容
     */
    private String content;

    /**
     * 校验ID
     */
    private String id;
}
