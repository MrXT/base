package com.project.common.controller;

import com.project.common.controller.vo.SystemSetUp;
import com.project.common.util.EncodeDecodeUtils;
import com.project.common.util.FastJsonUtils;
import com.project.common.util.ProperUtils;
import com.project.common.util.ValidateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/system")
public class SystemController {

    /**
     * 跳转关于我们页面
     *
     * @return
     */
    @RequestMapping(value = "/aboutUs", method = RequestMethod.GET)
    public String aboutUs(Map<String, Object> map) {
        // 读取系统配置属性文件，并进行base64解密
        Map<String, Object> result = ProperUtils.loadProperties("system.properties");
        for (Map.Entry<String, Object> stringObjectEntry : result.entrySet()) {
            if (ValidateUtils.isNotBlank((String) stringObjectEntry.getValue())) {
                result.put(stringObjectEntry.getKey(), EncodeDecodeUtils.base64Decode(stringObjectEntry.getValue().toString()));
            }
        }
        map.put("result", result);
        return "sys/setup/aboutUs";
    }

    /**
     * 用户规则页面
     *
     * @return
     */
    @RequestMapping(value = "/userRule", method = RequestMethod.GET)
    public String userRule(Map<String, Object> map) {
        // 读取系统配置属性文件，并进行base64解密
        Map<String, Object> result = ProperUtils.loadProperties("system.properties");
        for (Map.Entry<String, Object> stringObjectEntry : result.entrySet()) {
            if (ValidateUtils.isNotBlank((String) stringObjectEntry.getValue())) {
                result.put(stringObjectEntry.getKey(), EncodeDecodeUtils.base64Decode(stringObjectEntry.getValue().toString()));
            }
        }
        map.put("result", result);
        return "sys/setup/userRule";
    }

    /**
     * 跳转商业合作页面
     *
     * @return
     */
    @RequestMapping(value = "/businessCoop", method = RequestMethod.GET)
    public String businessCoop(Map<String, Object> map) {
        // 读取系统配置属性文件，并进行base64解密
        Map<String, Object> result = ProperUtils.loadProperties("system.properties");
        for (Map.Entry<String, Object> stringObjectEntry : result.entrySet()) {
            if (ValidateUtils.isNotBlank((String) stringObjectEntry.getValue())) {
                result.put(stringObjectEntry.getKey(), EncodeDecodeUtils.base64Decode(stringObjectEntry.getValue().toString()));
            }
        }
        map.put("result", result);
        return "sys/setup/businessCoop";
    }

    /**
     * 跳转市场合作页面
     *
     * @return
     */
    @RequestMapping(value = "/marketCoop", method = RequestMethod.GET)
    public String marketCoop(Map<String, Object> map) {
        // 读取系统配置属性文件，并进行base64解密
        Map<String, Object> result = ProperUtils.loadProperties("system.properties");
        for (Map.Entry<String, Object> stringObjectEntry : result.entrySet()) {
            if (ValidateUtils.isNotBlank((String) stringObjectEntry.getValue())) {
                result.put(stringObjectEntry.getKey(), EncodeDecodeUtils.base64Decode(stringObjectEntry.getValue().toString()));
            }
        }
        map.put("result", result);
        return "sys/setup/marketCoop";
    }

    /**
     * 跳转金币充值服务协议页面
     *
     * @return
     */
    @RequestMapping(value = "/coin", method = RequestMethod.GET)
    public String coin(Map<String, Object> map) {
        // 读取系统配置属性文件，并进行base64解密
        Map<String, Object> result = ProperUtils.loadProperties("system.properties");
        for (Map.Entry<String, Object> stringObjectEntry : result.entrySet()) {
            if (ValidateUtils.isNotBlank((String) stringObjectEntry.getValue())) {
                result.put(stringObjectEntry.getKey(), EncodeDecodeUtils.base64Decode(stringObjectEntry.getValue().toString()));
            }
        }
        map.put("result", result);
        return "sys/setup/coin";
    }

    /**
     * 系统设置修改接口
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(SystemSetUp condition) {
        Map<String, String> setUp = FastJsonUtils.beanToBean(condition, Map.class);
        // 对字段的值进行base64加密
        for (Map.Entry<String, String> stringObjectEntry : setUp.entrySet()) {
            if (stringObjectEntry.getValue() != null || ValidateUtils.isNotBlank(stringObjectEntry.getValue())) {
                setUp.put(stringObjectEntry.getKey(), EncodeDecodeUtils.base64Encode(stringObjectEntry.getValue().toString()));
            }
        }
        // 写入系统配置属性文件
        ProperUtils.updateProperties("system.properties", setUp);
        return 1;
    }
}
