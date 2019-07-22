package com.project.entity;

import com.project.common.bean.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 消息类（暂时没用） ClassName: SysMessage <br/>
 *
 * @version 2018年7月24日
 */
@Table(name = "sys_message")
public class SysMessage extends BaseEntity {

    @Id
    @Column(name = "message_id")
    @ApiModelProperty("")
    private String messageId;

    /**
     * 消息标题
     */
    @Column(name = "message_name")
    @ApiModelProperty("消息标题")
    private String messageName;

    @ApiModelProperty("消息图片地址(平台消息)")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 消息内容
     */
    @ApiModelProperty("消息内容")
    private String content;

    /**
     * 消息类型
     */
    @ApiModelProperty("消息类型 100100:平台消息 100101:系统消息")
    private String type;

    /**
     * 是否查看
     */
    @ApiModelProperty("是否查看")
    private Boolean scan;

    /**
     * 接受用户id
     */
    @Column(name = "user_id")
    @ApiModelProperty("接受用户id")
    private String userId;

    /**
     * 源id
     */
    @ApiModelProperty("源id")
    private String rid;

    /**
     * @return message_id
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * @param messageId
     */
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    /**
     * 获取系统消息名称
     *
     * @return message_name - 系统消息名称
     */
    public String getMessageName() {
        return messageName;
    }

    /**
     * 设置系统消息名称
     *
     * @param messageName 系统消息名称
     */
    public void setMessageName(final String messageName) {
        this.messageName = messageName;
    }

    /**
     * 获取消息内容
     *
     * @return content - 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     *
     * @param content 消息内容
     */
    public void setContent(final String content) {
        this.content = content;
    }

    /**
     * 获取消息类型 1:公告消息,2:普通消息(指定到user),
     *
     * @return type - 消息类型 1:公告消息,2:普通消息(指定到user),
     */
    public String getType() {
        return type;
    }

    /**
     * 设置消息类型 1:公告消息,2:普通消息(指定到user),
     *
     * @param type 消息类型 1:公告消息,2:普通消息(指定到user),
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * 获取是否查看
     *
     * @return scan - 是否查看
     */
    public Boolean getScan() {
        return scan;
    }

    /**
     * 设置是否查看
     *
     * @param scan 是否查看
     */
    public void setScan(final Boolean scan) {
        this.scan = scan;
    }

    /**
     * 获取接受用户id
     *
     * @return user_id - 接受用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置接受用户id
     *
     * @param userId 接受用户id
     */
    public void setUserId(final String userId) {
        this.userId = userId;
    }

    /**
     * 获取源id
     *
     * @return rid - 源id
     */
    public String getRid() {
        return rid;
    }

    /**
     * 设置源id
     *
     * @param rid 源id
     */
    public void setRid(final String rid) {
        this.rid = rid;
    }
}