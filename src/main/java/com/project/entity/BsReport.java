package com.project.entity;

import com.project.common.bean.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 举报类 ClassName: BsReport <br/>
 *
 * @version 2018年7月24日
 */
@Table(name = "bs_report")
public class BsReport extends BaseEntity {

    @Id
    @Column(name = "report_id")
    @ApiModelProperty("")
    private String reportId;

    /**
     * 举报名称
     */
    @Column(name = "report_name")
    @ApiModelProperty("举报名称")
    private String reportName;

    /**
     * 举报人id
     */
    @Column(name = "user_id")
    @ApiModelProperty("举报人id")
    private String userId;

    /**
     * 被举报人id
     */
    @Column(name = "report_user_id")
    @ApiModelProperty("被举报人id")
    private String reportUserId;

    /**
     * 举报类型
     */
    @ApiModelProperty("举报类型")
    private String type;

    /**
     * 举报类型
     */
    @ApiModelProperty("举报图片")
    private String pics;


    public String getPics() {
        return pics;
    }


    public void setPics(String pics) {
        this.pics = pics;
    }

    /**
     * 举报原因
     */
    @ApiModelProperty("举报原因")
    private String reason;

    /**
     * 举报结果
     */
    @ApiModelProperty("举报结果")
    private String result;

    /**
     * 处理状态
     */
    @ApiModelProperty("处理状态")
    private Boolean status;

    /**
     * @return report_id
     */
    public String getReportId() {
        return reportId;
    }

    /**
     * @param reportId
     */
    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    /**
     * 获取举报名称
     *
     * @return report_name - 举报名称
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * 设置举报名称
     *
     * @param reportName 举报名称
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * 获取举报人id
     *
     * @return user_id - 举报人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置举报人id
     *
     * @param userId 举报人id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取被举报人id
     *
     * @return report_user_id - 被举报人id
     */
    public String getReportUserId() {
        return reportUserId;
    }

    /**
     * 设置被举报人id
     *
     * @param reportUserId 被举报人id
     */
    public void setReportUserId(String reportUserId) {
        this.reportUserId = reportUserId;
    }

    /**
     * 获取举报类型
     *
     * @return type - 举报类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置举报类型
     *
     * @param type 举报类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取举报原因
     *
     * @return reason - 举报原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置举报原因
     *
     * @param reason 举报原因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 获取举报结果
     *
     * @return result - 举报结果
     */
    public String getResult() {
        return result;
    }

    /**
     * 设置举报结果
     *
     * @param result 举报结果
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 获取处理状态
     *
     * @return status - 处理状态
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置处理状态
     *
     * @param status 处理状态
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}