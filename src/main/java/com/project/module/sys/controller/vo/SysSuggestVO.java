package com.project.module.sys.controller.vo;

import com.project.common.annotation.Sort;
import com.project.entity.SysSuggest;
import io.swagger.annotations.ApiModelProperty;

/**
 * 意见反馈扩展参数类
 *
 * @version:2018-6-6
 */
public class SysSuggestVO extends SysSuggest {

    @ApiModelProperty("用户昵称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}