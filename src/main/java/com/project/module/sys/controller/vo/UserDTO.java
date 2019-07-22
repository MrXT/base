package com.project.module.sys.controller.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 用户扩展参数类
 *
 * @version:2017-2-7
 */
public class UserDTO extends UserVO {

    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;

    }

    /**
     * 是否有密码
     */
    @ApiModelProperty("是否有密码")
    private Boolean havePass;

    /**
     * 是否有支付密码
     */
    @ApiModelProperty("是否有支付密码")
    private Boolean havePayPass;

    public Boolean getHavePayPass() {
        return havePayPass;
    }

    public void setHavePayPass(Boolean havePayPass) {
        this.havePayPass = havePayPass;
    }

    public Boolean getHavePass() {
        return havePass;
    }

    public void setHavePass(Boolean havePass) {
        this.havePass = havePass;
    }

    /**
     * roleId
     */
    @Transient
    @ApiModelProperty(hidden = true)
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @ApiModelProperty("第三方注册类型,1:微信,2:QQ")
    private transient Integer type;

    @ApiModelProperty("第三方注册唯一码(不要超过32位)")
    private transient String unicode;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }
}