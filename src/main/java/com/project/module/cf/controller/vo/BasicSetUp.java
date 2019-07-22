package com.project.module.cf.controller.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 基本配置类 ClassName: BasicSetUp <br/>
 *
 * @version 2018年7月24日
 */
public class BasicSetUp {

    /**
     * 配置
     */
    @ApiModelProperty("配置")
    private String config;

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}
