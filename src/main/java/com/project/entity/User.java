package com.project.entity;

import com.project.common.bean.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户信息类 ClassName: User <br/>
 *
 * @version 2018年7月24日
 */
@Table(name = "sys_user")
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty("性别 1:女 2：男")
    private Integer sex;


    @ApiModelProperty("出生日期:格式yyyy-mm-dd")
    private String birth;

    @ApiModelProperty("最后的经度")
    private Double lastLng;

    @ApiModelProperty("邮件")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @ApiModelProperty("最后的经度")
    private Double lastLat;

    @ApiModelProperty("余额")
    private Long balance;

    @ApiModelProperty("财富值")
    private Long integrate;

    @ApiModelProperty("身份证")
    private String idCard;

    @ApiModelProperty("银行卡")
    private String bank;

    @ApiModelProperty("银行卡类型")
    private String bankType;

    @ApiModelProperty("真实姓名")
    private String realName;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Long getIntegrate() {
        return integrate;
    }

    public void setIntegrate(Long integrate) {
        this.integrate = integrate;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Double getLastLng() {
        return lastLng;
    }

    public void setLastLng(Double lastLng) {
        this.lastLng = lastLng;
    }

    public Double getLastLat() {
        return lastLat;
    }

    public void setLastLat(Double lastLat) {
        this.lastLat = lastLat;
    }

    @ApiModelProperty("微信id")
    private String wxid;

    @ApiModelProperty("qqid")
    private String qqid;

    public String getQqid() {
        return qqid;
    }

    public void setQqid(String qqid) {
        this.qqid = qqid;
    }

    @ApiModelProperty("未二次加密的密码")
    private String pass;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @ApiModelProperty("app注册用户")
    @Column(name = "app_user")
    private Boolean appUser;

    public Boolean getAppUser() {
        return appUser;
    }

    public void setAppUser(Boolean appUser) {
        this.appUser = appUser;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    /**
     * 状态 0:待审核 1:未通过 2:正常 3:锁定
     */
    @ApiModelProperty("状态 0:待审核 1:未通过 2:正常 3:锁定")
    private Integer status;

    @ApiModelProperty("头像URL(公共接口-文件上传返回的地址)")
    @Column(name = "head_url")
    private String headUrl;

    @ApiModelProperty("背景URL")
    private String background;

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    /**
     * @return the headUrl
     */
    public String getHeadUrl() {
        return headUrl;
    }

    /**
     * @param headUrl the headUrl to set
     */
    public void setHeadUrl(final String headUrl) {
        this.headUrl = headUrl;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final Integer status) {
        this.status = status;
    }

    /**
     * 用户名
     */
    @ApiModelProperty("登录名称（手机号）")
    private String username;

    /**
     * 姓名
     */
    @ApiModelProperty("用户昵称")
    private String name;

    @ApiModelProperty("密码(MD5加密)")
    private String password;

    private String salt;

    /**
     * 电话
     */
    @ApiModelProperty("手机")
    private String phone;

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     */
    public void setSalt(final String salt) {
        this.salt = salt;
    }

    /**
     * 获取手机
     *
     * @return phone - 手机
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机
     *
     * @param phone 手机
     */
    public void setPhone(final String phone) {
        this.phone = phone;
    }

    /**
     * 支付密码
     */
    @ApiModelProperty("支付密码(MD5加密)")
    @Column(name = "pay_pass")
    private String payPass;

    /**
     * 个性签名（200字）
     */
    @ApiModelProperty("个性签名（200字）")
    private String sign;


    @ApiModelProperty("标签(已英文逗号隔开)")
    private String tags;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPayPass() {
        return payPass;
    }

    public void setPayPass(String payPass) {
        this.payPass = payPass;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    @ApiModelProperty("微信号")
    private String wxno;

    @ApiModelProperty("qq号")
    private String qqno;

    @ApiModelProperty("微信二维码(地址)")
    private String wxcode;


    public String getWxno() {
        return wxno;
    }

    public void setWxno(String wxno) {
        this.wxno = wxno;
    }

    public String getQqno() {
        return qqno;
    }

    public void setQqno(String qqno) {
        this.qqno = qqno;
    }

    public String getWxcode() {
        return wxcode;
    }

    public void setWxcode(String wxcode) {
        this.wxcode = wxcode;
    }


}