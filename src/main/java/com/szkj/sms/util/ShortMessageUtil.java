package com.szkj.sms.util;

import com.szkj.sms.config.TencentSmsConfig;
import com.szkj.sms.vo.SendWarningVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;

import java.util.Random;

/**
 * 短信工具类
 *
 * @Author: <a href="http://sixiwenwu.com"/>
 * @Date: 2021/10/7 11:17
 */
@Component
public class ShortMessageUtil {

    private final Logger logger = LoggerFactory.getLogger(ShortMessageUtil.class);
    private final String WARNING_TEMPLATEID = "000000";

    public JsonResult sendWarningMessage(SendWarningVo sendWarningVo) {
        if (sendWarningVo == null || !"csszkj".equals(sendWarningVo.getId()) || StringUtils.isAnyBlank(sendWarningVo.getPhoneNum(), sendWarningVo.getDtu(), sendWarningVo.getDevice(), sendWarningVo.getNoc(), sendWarningVo.getData(), sendWarningVo.getBeyond(), sendWarningVo.getTime())) {
            return JsonResult.error("参数异常");
        }
        try {
            logger.error("发送传感器数据异常警告短信,模板参数={}", sendWarningVo);
            // 实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
            Credential cred = new Credential(TencentSmsConfig.SECRETID, TencentSmsConfig.SECRETKEY);
            // 实例化一个 http 选项，可选，无特殊需求时可以跳过
            HttpProfile httpProfile = new HttpProfile();
            // SDK 会自动指定域名，通常无需指定域名，但访问金融区的服务时必须手动指定域名
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            /* 非必要步骤:
             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化 SMS 的 client 对象,第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量 */
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
            SendSmsRequest req = new SendSmsRequest();
            /* 电话号码 */
            String[] phoneNumberSet1 = {"86" + sendWarningVo.getPhoneNum()};
            req.setPhoneNumberSet(phoneNumberSet1);
            /* 短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666 */
            req.setSmsSdkAppid(TencentSmsConfig.SMSSDKAPPID);
            req.setSign(TencentSmsConfig.SIGNNAME);
            /* 模板 ID: 必须填写已审核通过的模板 ID，可登录 [短信控制台] 查看模板 ID */
            req.setTemplateID(WARNING_TEMPLATEID);
            /* 模板参数: 若无模板参数，则设置为空*/
            String[] templateParamSet1 = {sendWarningVo.getDtu(), sendWarningVo.getDevice(), sendWarningVo.getNoc(), sendWarningVo.getData(), sendWarningVo.getBeyond(), sendWarningVo.getTime()};
            req.setTemplateParamSet(templateParamSet1);
            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            client.SendSms(req);
            // 输出 JSON 格式的字符串回包
            // System.out.println(SendSmsResponse.toJsonString(resp));
            logger.info("短信发送成功");
            return JsonResult.success();
        } catch (TencentCloudSDKException e) {
            logger.error("短信发送异常={e}", e);
            return JsonResult.error("发送短信发生异常" + e.getMessage());
        }
    }

    /**
     * 随机生成验证码
     *
     * @param length 长度为4位或者6位
     * @return
     */
    public static String generateValidateCode(int length) {
        Integer code = null;
        if (length == 4) {
            //生成随机数，最大为9998
            code = new Random().nextInt(9999);
            if (code < 1000) {
                //保证随机数为4位数字
                code = code + 1000;
            }
        } else if (length == 6) {
            //生成随机数，最大为999999
            code = new Random().nextInt(999999);
            if (code < 100000) {
                //保证随机数为6位数字
                code = code + 100000;
            }
        } else {
            throw new RuntimeException("只能生成4位或6位数字验证码");
        }
        return String.valueOf(code);
    }
}
