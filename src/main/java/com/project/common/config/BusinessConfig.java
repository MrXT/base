package com.project.common.config;

import com.project.common.bean.BaseEntity;
import com.project.common.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务相关配置类 ClassName: BusinessConfig <br/>
 * Package Name:com.karakal.config
 *
 * @version 1.0 Date:2016年7月28日下午2:00:36 Copyright (c) 2016, manzz.com All Rights Reserved.
 */
@Configuration
@EnableAsync
@EnableScheduling
public class BusinessConfig {
    @Value("${pageSize}")
    private Integer pageSize;

    @Bean(name = "statusMap")
    public Map<Integer, String> statusMap() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        BaseEntity.ROWS = pageSize;
        map.put(SystemConstant.BAD_REQUEST, "BAD_REQUEST,请求参数错误");
        map.put(SystemConstant.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED,请求方法错误");
        map.put(SystemConstant.NOT_FOUND, "NOT_FOUND,请求地址没找到");
        map.put(SystemConstant.TOKEN_INVALID, "TOKEN失效！");
        return map;
    }

}
