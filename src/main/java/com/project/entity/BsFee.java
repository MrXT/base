package com.project.entity;

import com.project.common.bean.BaseEntity;
import com.project.common.spring.SessionHolder;
import com.project.common.util.CommonUtils;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 消费类 ClassName: BsFee <br/>
 *
 * @version 2018年7月24日
 */
@Table(name = "bs_fee")
public class BsFee extends BaseEntity {

    @Id
    @Column(name = "fee_id")
    @ApiModelProperty("")
    private String feeId;

    /**
     * 消费名称
     */
    @Column(name = "fee_name")
    @ApiModelProperty("消费名称")
    private String feeName;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 对方用户id
     */
    @Column(name = "fee_user_id")
    @ApiModelProperty("对方用户id")
    private String feeUserId;

    /**
     * 消费类型
     */
    @ApiModelProperty("消费类型")
    private Integer type;

    /**
     * 消费金币数
     */
    @ApiModelProperty("消费贝壳数")
    private Integer coin;

    /**
     * @return fee_id
     */
    public String getFeeId() {
        return feeId;
    }

    /**
     * @param feeId
     */
    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    /**
     * 获取消费名称
     *
     * @return fee_name - 消费名称
     */
    public String getFeeName() {
        return feeName;
    }

    /**
     * 设置消费名称
     *
     * @param feeName 消费名称
     */
    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取对方用户id
     *
     * @return fee_user_id - 对方用户id
     */
    public String getFeeUserId() {
        return feeUserId;
    }

    /**
     * 设置对方用户id
     *
     * @param feeUserId 对方用户id
     */
    public void setFeeUserId(String feeUserId) {
        this.feeUserId = feeUserId;
    }

    /**
     * 获取消费类型
     *
     * @return type -
     * 消费类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置消费类型
     *
     * @param type 消费类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取消费金币数
     *
     * @return coin - 消费金币数
     */
    public Integer getCoin() {
        return coin;
    }

    /**
     * 设置消费金币数
     *
     * @param coin 消费金币数
     */
    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    /**
     * 主要用户创建消费记录使用
     *
     * @param type 类型
     * @param coin 金币
     */
    public BsFee(Integer type, Integer coin) {
        this.setDefaultValue();
        this.setCtime(new Date());
        this.feeId = CommonUtils.genId();
        this.userId = SessionHolder.getId();
        this.type = type;
        this.coin = coin;
    }

    /**
     * 主要用户创建消费记录使用
     *
     * @param type      类型
     * @param coin      金币
     * @param feeUserId 对方用户id
     */
    public BsFee(Integer type, Integer coin, String feeUserId) {
        this.setDefaultValue();
        this.setCtime(new Date());
        this.feeId = CommonUtils.genId();
        this.userId = SessionHolder.getId();
        this.type = type;
        this.coin = coin;
        this.feeUserId = feeUserId;
    }

    public BsFee() {
    }
}