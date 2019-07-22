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
 * 收益类 ClassName: BsIncome <br/>
 *
 * @version 2018年7月24日
 */
@Table(name = "bs_income")
public class BsIncome extends BaseEntity {

    @Id
    @Column(name = "income_id")
    @ApiModelProperty("")
    private String incomeId;

    /**
     * 收益名称
     */
    @Column(name = "income_name")
    @ApiModelProperty("收益名称")
    private String incomeName;

    /**
     * 收益人id
     */
    @Column(name = "user_id")
    @ApiModelProperty("收益人id")
    private String userId;

    /**
     * 收益来源用户id
     */
    @Column(name = "income_user_id")
    @ApiModelProperty("收益来源用户id")
    private String incomeUserId;

    /**
     * 收益类型
     */
    @ApiModelProperty("收益类型")
    private Integer type;

    /**
     * 金币数
     */
    @ApiModelProperty("金币数")
    private Integer coin;

    /**
     * @return income_id
     */
    public String getIncomeId() {
        return incomeId;
    }

    /**
     * @param incomeId
     */
    public void setIncomeId(String incomeId) {
        this.incomeId = incomeId;
    }

    /**
     * 获取收益名称
     *
     * @return income_name - 收益名称
     */
    public String getIncomeName() {
        return incomeName;
    }

    /**
     * 设置收益名称
     *
     * @param incomeName 收益名称
     */
    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    /**
     * 获取收益人id
     *
     * @return user_id - 收益人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置收益人id
     *
     * @param userId 收益人id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取收益来源用户id
     *
     * @return income_user_id - 收益来源用户id
     */
    public String getIncomeUserId() {
        return incomeUserId;
    }

    /**
     * 设置收益来源用户id
     *
     * @param incomeUserId 收益来源用户id
     */
    public void setIncomeUserId(String incomeUserId) {
        this.incomeUserId = incomeUserId;
    }

    /**
     * 获取收益类型
     *
     * @return type - 收益类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置收益类型
     *
     * @param type 收益类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取金币数
     *
     * @return coin - 金币数
     */
    public Integer getCoin() {
        return coin;
    }

    /**
     * 设置金币数
     *
     * @param coin 金币数
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
    public BsIncome(Integer type, Integer coin) {
        this.setDefaultValue();
        this.setCtime(new Date());
        this.incomeId = CommonUtils.genId();
        this.userId = SessionHolder.getId();
        this.type = type;
        this.coin = coin;
    }

    /**
     * 主要用户创建消费记录使用
     *
     * @param type         类型
     * @param coin         金币
     * @param userId       用户id
     * @param incomeUserId 收益来源用户id
     */
    public BsIncome(Integer type, Integer coin, String userId, String incomeUserId) {
        this.setUtime(new Date());
        this.setCtime(new Date());
        this.incomeId = CommonUtils.genId();
        this.userId = userId;
        this.type = type;
        this.coin = coin;
        this.incomeUserId = incomeUserId;
    }

    public BsIncome() {
    }
}