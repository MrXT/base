package com.project.module.sys.controller.vo;

import com.project.common.annotation.Sort;
import com.project.entity.SysMessage;
import io.swagger.annotations.ApiModelProperty;

/**
 * 消息扩展参数类
 *
 * @version:2017-10-24
 */
public class SysMessageVO extends SysMessage {

    @ApiModelProperty("接收用户昵称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}