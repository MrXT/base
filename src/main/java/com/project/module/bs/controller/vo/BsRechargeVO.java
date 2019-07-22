package com.project.module.bs.controller.vo;

import com.project.common.annotation.Sort;
import com.project.entity.BsRecharge;
import io.swagger.annotations.ApiModelProperty;

/**
 * 充值扩展参数类
 *
 * @version:2018-6-29
 */
public class BsRechargeVO extends BsRecharge {

    @ApiModelProperty("充值用户昵称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}