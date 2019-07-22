
package com.project.module.bs.controller.vo;

import com.project.common.annotation.Sort;
import com.project.entity.BsReport;
import io.swagger.annotations.ApiModelProperty;

/**
 * 举报扩展参数类
 *
 * @version:2018-6-25
 */
public class BsReportVO extends BsReport {

    @ApiModelProperty("举报人昵称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 被举报人昵称
     */
    @ApiModelProperty("被举报人昵称")
    private String reportUserName;

    public String getReportUserName() {
        return reportUserName;
    }

    public void setReportUserName(String reportUserName) {
        this.reportUserName = reportUserName;
    }

    /**
     * 处理管理员
     */
    @ApiModelProperty("处理管理员")
    private String uName;

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}