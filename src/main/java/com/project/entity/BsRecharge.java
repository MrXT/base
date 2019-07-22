package com.project.entity;

import com.project.common.bean.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 充值类 ClassName: BsRecharge <br/>
 *
 * @version 2018年7月24日
 */
@Table(name = "bs_recharge")
public class BsRecharge extends BaseEntity {

    @Id
    @Column(name = "recharge_id")
    @ApiModelProperty("")
    private String rechargeId;

    /**
     * 充值名称
     */
    @Column(name = "recharge_name")
    @ApiModelProperty("充值名称")
    private String rechargeName;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    @ApiModelProperty("用户id")
    private String userId;

    /**
     * 充值金币
     */
    @ApiModelProperty("充值金币")
    private Integer coin;

    /**
     * 支付金额
     */
    @ApiModelProperty("支付金额")
    private Double money;

    /**
     * 支付方式1:支付宝,2:微信,3:苹果内购
     */
    @Column(name = "pay_type")
    @ApiModelProperty("支付方式1:支付宝,2:微信,3:苹果内购")
    private Integer payType;

    /**
     * @return recharge_id
     */
    public String getRechargeId() {
        return rechargeId;
    }

    /**
     * @param rechargeId
     */
    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    /**
     * 获取充值名称
     *
     * @return recharge_name - 充值名称
     */
    public String getRechargeName() {
        return rechargeName;
    }

    /**
     * 设置充值名称
     *
     * @param rechargeName 充值名称
     */
    public void setRechargeName(String rechargeName) {
        this.rechargeName = rechargeName;
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
     * 获取充值金币
     *
     * @return coin - 充值金币
     */
    public Integer getCoin() {
        return coin;
    }

    /**
     * 设置充值金币
     *
     * @param coin 充值金币
     */
    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    /**
     * 获取支付金额
     *
     * @return money - 支付金额
     */
    public Double getMoney() {
        return money;
    }

    /**
     * 设置支付金额
     *
     * @param money 支付金额
     */
    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     * 获取支付方式1:支付宝,2:微信,3:苹果内购
     *
     * @return pay_type - 支付方式1:支付宝,2:微信,3:苹果内购
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置支付方式1:支付宝,2:微信,3:苹果内购
     *
     * @param payType 支付方式1:支付宝,2:微信,3:苹果内购
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }
}