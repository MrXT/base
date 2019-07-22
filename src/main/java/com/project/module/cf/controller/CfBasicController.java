package com.project.module.cf.controller;

import com.project.common.util.FastJsonUtils;
import com.project.common.util.ProperUtils;
import com.project.module.cf.controller.vo.BasicSetUp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 基本配置控制器
 *
 * @version 2018年7月4日
 */
@Controller
@RequestMapping("/cf")
public class CfBasicController {

    /**
     * 修改基本配置
     *
     * @return
     */
    @RequestMapping("/basic")
    public Object manage(Map<String, Object> map) {
        // 加载配置信息
        Map<String, Object> result = ProperUtils.loadProperties("config.properties");
        map.put("result", result);
        return "cf/basic/basic";
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(BasicSetUp condition) {
        Map<String, String> setUp = FastJsonUtils.beanToBean(condition, Map.class);
        // 写入系统配置属性文件
        ProperUtils.updateProperties("config.properties", setUp);
        return 1;
    }
}
