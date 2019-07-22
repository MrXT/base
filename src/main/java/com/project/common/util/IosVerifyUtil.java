package com.project.common.util;

import com.alibaba.fastjson.JSONObject;
import com.project.common.exception.BusinessException;
import com.project.common.spring.SessionHolder;

import javax.net.ssl.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 苹果IAP内购验证工具类
 *
 * @ClassName: IosVerify
 * @Description:Apple Pay
 */
public class IosVerifyUtil {

    /**
     * TrustAnyTrustManager 验证管理器 ClassName: TrustAnyTrustManager <br/>
     *
     * @version 2018年7月24日 IosVerifyUtil
     */
    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    /**
     * 域名验证类 ClassName: TrustAnyHostnameVerifier <br/>
     *
     * @version 2018年7月24日 IosVerifyUtil
     */
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {

        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * ios沙箱验证地址
     */
    private static final String url_sandbox = "https://sandbox.itunes.apple.com/verifyReceipt";

    /**
     * ios正式环境验证地址
     */
    private static final String url_verify = "https://buy.itunes.apple.com/verifyReceipt";

    /**
     * 苹果服务器验证
     *
     * @param receipt 账单
     * @return null 或返回结果 沙盒 https://sandbox.itunes.apple.com/verifyReceipt
     * @url 要验证的地址
     */
    public static String buyAppVerify(String receipt, int type) {
        // 环境判断 线上/开发环境用不同的请求链接
        String url = "";
        if (type == 0) {
            // 沙盒测试
            url = url_sandbox;
        } else {
            // 线上测试
            url = url_verify;
        }
        // String url = EnvUtils.isOnline() ?url_verify : url_sandbox;

        try {
            // 封装ssl请求
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL console = new URL(url);
            // 发起链接
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.setRequestMethod("POST");
            // 设置请求类型
            conn.setRequestProperty("content-type", "text/json");
            conn.setRequestProperty("Proxy-Connection", "Keep-Alive");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            BufferedOutputStream hurlBufOus = new BufferedOutputStream(conn.getOutputStream());

            // IOS验证的参数字段
            String str = String.format(Locale.CHINA, "{\"receipt-data\":\"" + receipt + "\"}");// 拼成固定的格式传给平台
            hurlBufOus.write(str.getBytes());
            hurlBufOus.flush();

            // 响应结果流
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            return sb.toString();
        } catch (Exception ex) {
            LogUtils.LOGEXCEPTION(ex);
            throw new BusinessException("苹果服务器验证异常");
        }
    }

    /**
     * 用BASE64加密
     *
     * @param str
     * @return
     */
    public static String getBASE64(String str) {
        byte[] b = str.getBytes();
        String s = null;
        if (b != null) {
            s = EncodeDecodeUtils.base64Encode(b);
        }
        return s;
    }

    /**
     * @param payload：收据
     * @param coin       金币数
     * @throws Exception 苹果内购支付
     * @throws
     * @Title: doIosRequest
     * @Description:Ios客户端内购支付
     */
    public static Map<String, Object> doIosRequest(String payload, int coin) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 1.先线上测试 发送平台验证
        String verifyResult = IosVerifyUtil.buyAppVerify(payload, 1);
        if (verifyResult == null) {
            // 苹果服务器没有返回验证结果
            throw new BusinessException("无订单信息!");
        } else {
            // 苹果验证有返回结果
            LogUtils.INFO("线上，苹果平台返回JSON:" + verifyResult);
            JSONObject job = JSONObject.parseObject(verifyResult);
            String states = job.getString("status");
            // 是沙盒环境，应沙盒测试，否则执行下面
            if ("21007".equals(states)) {
                // 2.沙盒测试 发送平台验证
                verifyResult = IosVerifyUtil.buyAppVerify(payload, 0);
                LogUtils.INFO("沙盒环境，苹果平台返回JSON:" + verifyResult);
                job = JSONObject.parseObject(verifyResult);
                states = job.getString("status");
            }

            LogUtils.INFO("苹果平台返回值：job" + job);
            // 前端所提供的收据是有效的 验证成功
            if (states.equals("0")) {
                String r_receipt = job.getString("receipt");
                JSONObject returnJson = JSONObject.parseObject(r_receipt);
                String inApp = returnJson.getString("in_app");
                JSONObject inAppJson = JSONObject.parseObject(inApp.substring(1, inApp.length() - 1));

                String productId = inAppJson.getString("product_id");
                String appTransactionId = inAppJson.getString("transaction_id"); // 订单号
                /************************************************ +自己的业务逻辑 **********************************************************/
                // 如果单号一致 则保存到数据库
                String[] moneys = productId.split("\\.");
                map.put("money", Double.parseDouble(moneys[moneys.length - 1]));
                map.put("rechargeId", appTransactionId);
                map.put("userId", SessionHolder.getId());
                map.put("coin", coin);
                // 苹果内购
                map.put("payType", 3);
                return map;
            } else {
                throw new BusinessException("Payload数据错误");
            }
        }
    }

}