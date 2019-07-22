package com.project.module.bs.controller.vo;

import com.project.common.annotation.Sort;
import com.project.entity.BsIncome;
import io.swagger.annotations.ApiModelProperty;

/**
 * 收益扩展类
 *
 * @version:2018-7-13
 */
public class BsIncomeVO extends BsIncome {

    @ApiModelProperty("收益用户")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}