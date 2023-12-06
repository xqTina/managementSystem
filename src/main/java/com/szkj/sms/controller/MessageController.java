package com.szkj.sms.controller;

import com.szkj.sms.config.PostMailConfig;
import com.szkj.sms.util.JsonResult;
import com.szkj.sms.util.MailUtil;
import com.szkj.sms.util.ShortMessageUtil;
import com.szkj.sms.vo.SendMailVo;
import com.szkj.sms.vo.SendWarningVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 系统通知控制类
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/10/6 20:31
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    private final MailUtil mailUtil;
    private final ShortMessageUtil shortMessageUtil;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Autowired
    public MessageController(MailUtil mailUtil,ShortMessageUtil shortMessageUtil){
        this.mailUtil = mailUtil;
        this.shortMessageUtil = shortMessageUtil;
    }

    /**
     * 发送简单邮件
     * @param sendMailVo 发送对象
     * @return JsonResult
     */
    @ResponseBody
    @PostMapping("/email/send")
    public JsonResult sendSimpleMail(@RequestBody(required = false) SendMailVo sendMailVo){
        return mailUtil.simpleMail(sendMailVo);
    }

    /**
     * 发送测试邮件
     * @return JsonResult
     */
    @ResponseBody
    @PostMapping("/email/send/test")
    public JsonResult sendSimpleMail(String to){
        return mailUtil.simpleMail(SendMailVo.builder().to(to).subject("测试邮件").content("传感器数据管理系统发件人测试邮件，测试时间："+ simpleDateFormat.format(new Date())).id("csszkj").build());
    }

    /**
     * 发送数据异常警告短信
     * @param sendWarningVo 发送对象
     * @return JsonResult
     */
    @ResponseBody
    @PostMapping("/sms/warn")
    public JsonResult sendSimpleMail(@RequestBody(required = false) SendWarningVo sendWarningVo){
        return shortMessageUtil.sendWarningMessage(sendWarningVo);
    }

    @ResponseBody
    @PostMapping("/email/config")
    public JsonResult reloadEmailConfig(@RequestBody PostMailConfig postMailConfig){
        JavaMailSenderImpl newJms = mailUtil.configEmail(postMailConfig);
        if (newJms==null||!(Objects.requireNonNull(newJms.getUsername())).equals(postMailConfig.getMailUsername())){
            return JsonResult.error("更新失败");
        }
        return JsonResult.success();
    }


    @ApiOperation(value = "邮件配置页面路由")
    @GetMapping("/email/edit")
    public String systemEmail(Model model) {
        Map<String, String> emailMap = new HashMap<>();
        emailMap.put("腾讯企业邮箱","smtp.exmail.qq.com");
        emailMap.put("QQ邮箱","smtp.qq.com");
        emailMap.put("网易163邮箱","smtp.163.com");
        emailMap.put("阿里邮箱企业版","smtp.qiye.aliyun.com");
        emailMap.put("钉钉企业邮箱","smtp.mxhichina.com");
        /*outlook ssl 587*/
        emailMap.put("Outlook","smtp.office365.com");
        emailMap.put("Gmail","gmail-smtp-in.l.google.com");
        model.addAttribute("email",mailUtil.getEmailConfig());
        model.addAttribute("hosts",emailMap);
        return "email";
    }
}
