package com.project.entity;

import com.project.common.bean.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 意见反馈类 ClassName: SysSuggest <br/>
 *
 * @version 2018年7月24日
 */
@Table(name = "sys_suggest")
public class SysSuggest extends BaseEntity {

    @Id
    @Column(name = "suggest_id")
    @ApiModelProperty("")
    private String suggestId;

    /**
     * 意见反馈名称
     */
    @Column(name = "suggest_name")
    @ApiModelProperty("意见反馈名称")
    private String suggestName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("图片地址")
    private String pics;

    @ApiModelProperty("处理结果")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    /**
     * 反馈人id
     */
    @Column(name = "user_id")
    @ApiModelProperty("反馈人id")
    private String userId;

    /**
     * 反馈内容
     */
    @ApiModelProperty("反馈内容")
    private String content;

    /**
     * 状态0:未读，1：已读
     */
    @ApiModelProperty("状态0:未读，1：已读")
    private Boolean status;

    /**
     * @return suggest_id
     */
    public String getSuggestId() {
        return suggestId;
    }

    /**
     * @param suggestId
     */
    public void setSuggestId(String suggestId) {
        this.suggestId = suggestId;
    }

    /**
     * 获取意见反馈名称
     *
     * @return suggest_name - 意见反馈名称
     */
    public String getSuggestName() {
        return suggestName;
    }

    /**
     * 设置意见反馈名称
     *
     * @param suggestName 意见反馈名称
     */
    public void setSuggestName(String suggestName) {
        this.suggestName = suggestName;
    }

    /**
     * 获取反馈人id
     *
     * @return user_id - 反馈人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置反馈人id
     *
     * @param userId 反馈人id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取反馈内容
     *
     * @return content - 反馈内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置反馈内容
     *
     * @param content 反馈内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取状态0:未读，1：已读
     *
     * @return status - 状态0:未读，1：已读
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 设置状态0:未读，1：已读
     *
     * @param status 状态0:未读，1：已读
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }
}