package com.project.common.controller.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 系统设置
 */
public class SystemSetUp {

    /**
     * 关于我们（html代码字符串)
     */
    @ApiModelProperty("关于我们（html代码字符串）")
    private String aboutUs;

    /**
     * 商务合作
     */
    @ApiModelProperty("商务合作（html代码字符串）")
    private String businessCoop;

    /**
     * 市场合作
     */
    @ApiModelProperty("市场合作（html代码字符串）")
    private String marketCoop;

    /**
     * 客户电话
     */
    @ApiModelProperty("客户电话")
    private String contactUs;

    public String getContactUs() {
        return contactUs;
    }

    public void setContactUs(String contactUs) {
        this.contactUs = contactUs;
    }

    /**
     * 用户协议（html代码字符串）
     */
    @ApiModelProperty("用户协议（html代码字符串）")
    private String userRule;


    /**
     * 用户协议（html代码字符串）
     */
    @ApiModelProperty("金币充值服务协议（html代码字符串）")
    private String coin;


    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getUserRule() {
        return userRule;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getMarketCoop() {
        return marketCoop;
    }

    public void setMarketCoop(String marketCoop) {
        this.marketCoop = marketCoop;
    }

    public void setUserRule(String userRule) {
        this.userRule = userRule;
    }

    public String getBusinessCoop() {

        return businessCoop;
    }

    public void setBusinessCoop(String businessCoop) {
        this.businessCoop = businessCoop;
    }
}
