package com.project.module.bs.controller.vo;

import com.project.common.annotation.Sort;
import com.project.entity.BsFee;
import io.swagger.annotations.ApiModelProperty;

/**
 * 消费扩展类
 *
 * @version:2018-6-29
 */
public class BsFeeVO extends BsFee {

    /**
     * 对方昵称
     */
    @ApiModelProperty("对方昵称")
    private String feeUserName;

    public String getFeeUserName() {
        return feeUserName;
    }

    public void setFeeUserName(String feeUserName) {
        this.feeUserName = feeUserName;
    }

    @ApiModelProperty("消费用户昵称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}