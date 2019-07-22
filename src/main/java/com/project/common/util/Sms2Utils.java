package com.project.common.util;

import com.alibaba.fastjson.JSON;
import com.project.common.util.mail.EmailUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sms2Utils {

    /**
     * 账户id
     */
    private static String smsAccountId = CommonUtils.readResource("smsAccountId");
    /**
     * 账户token
     */
    private static String smsToken = CommonUtils.readResource("smsToken");
    /**
     * api
     */
    private static String smsApi = CommonUtils.readResource("smsApi");
    /**
     * appid
     */
    private static String smsAppId = CommonUtils.readResource("smsAppId");
    /**
     * 模板id
     */
    private static String smsTemplateId = CommonUtils.readResource("smsTemplateId");

    /**
     * 发送验证码短信
     *
     * @param phoneNum 手机号
     * @param code     短信模板
     * @return
     */
    public static void sendMsg(final String phoneNum, final String code) {

        String timestamp = DateUtils.toFormatDateString(new Date(), "yyyyMMddHHmmss");
        String url = smsApi + "sig=" + EncodeDecodeUtils.md5Digest(smsAccountId + smsToken + timestamp).toUpperCase();

        /**
         * 请求体
         */
        Map<String, Object> body = new HashMap<>();
        body.put("to", phoneNum);
        body.put("appId", smsAppId);
        body.put("templateId", smsTemplateId);
        body.put("datas", new String[]{code, "10分钟"});

        String requestJson = JSON.toJSONString(body);

        LogUtils.DEBUG("before request string is: " + requestJson);

        /**
         * 请求头
         */
        String authorization = EncodeDecodeUtils.base64Encode(smsAccountId + ":" + timestamp);

        String response = ChuangLanSmsUtil.sendSmsByPost(url, requestJson, authorization);

        LogUtils.DEBUG("response after request result is :" + response);
    }

    public static void sendEmail(final String email, final String code) {
        EmailUtils.sendEmail(email, "验证码", "您的验证码是" + code);
    }

    public static void main(final String[] args) {
        //sendEmail("734814548@qq.com", "123456");
        sendMsg("18200390083","123456");
    }

}
