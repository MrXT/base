package com.project.entity;

import com.project.common.bean.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 金币充值配置类 ClassName: CfCoinBuy <br/>
 *
 * @version 2018年7月24日
 */
@Table(name = "cf_coin_buy")
public class CfCoinBuy extends BaseEntity {

    @Id
    @Column(name = "coin_buy_id")
    @ApiModelProperty("参数购买配置id")
    private String coinBuyId;

    /**
     * 金币购买配置名称
     */
    @Column(name = "coin_buy_name")
    @ApiModelProperty(hidden = true)
    private String coinBuyName;

    /**
     * 充值金额
     */
    @ApiModelProperty("充值金额")
    private Double money;

    /**
     * 安卓用户获得金币数
     */
    @ApiModelProperty("用户获得金币数")
    private Integer android;

    /**
     * 用户获得金币数
     */
    @ApiModelProperty(hidden = true)
    private Integer ios;

    /**
     * @return coin_buy_id
     */
    public String getCoinBuyId() {
        return coinBuyId;
    }

    /**
     * @param coinBuyId
     */
    public void setCoinBuyId(String coinBuyId) {
        this.coinBuyId = coinBuyId;
    }

    /**
     * 获取金币购买配置名称
     *
     * @return coin_buy_name - 金币购买配置名称
     */
    public String getCoinBuyName() {
        return coinBuyName;
    }

    /**
     * 设置金币购买配置名称
     *
     * @param coinBuyName 金币购买配置名称
     */
    public void setCoinBuyName(String coinBuyName) {
        this.coinBuyName = coinBuyName;
    }

    /**
     * 获取充值金额
     *
     * @return money - 充值金额
     */
    public Double getMoney() {
        return money;
    }

    /**
     * 设置充值金额
     *
     * @param money 充值金额
     */
    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     * 获取安卓用户获得金币数
     *
     * @return android - 安卓用户获得金币数
     */
    public Integer getAndroid() {
        return android;
    }

    /**
     * 设置安卓用户获得金币数
     *
     * @param android 安卓用户获得金币数
     */
    public void setAndroid(Integer android) {
        this.android = android;
    }

    /**
     * 获取ios用户获得金币数
     *
     * @return ios - ios用户获得金币数
     */
    public Integer getIos() {
        return ios;
    }

    /**
     * 设置ios用户获得金币数
     *
     * @param ios ios用户获得金币数
     */
    public void setIos(Integer ios) {
        this.ios = ios;
    }
}