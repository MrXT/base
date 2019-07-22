package com.project.entity;

import com.project.common.bean.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_banner")
public class SysBanner extends BaseEntity {
    /**
     * Banner图Id
     */
    @Id
    @Column(name = "banner_id")
    @ApiModelProperty("Banner图Id")
    private String bannerId;

    /**
     * Banner图名称
     */
    @Column(name = "banner_name")
    @ApiModelProperty("Banner图名称")
    private String bannerName;

    /**
     * 图片
     */
    @ApiModelProperty("图片")
    private String pic;

    /**
     * 内容(url或者html5代码)
     */
    @ApiModelProperty("内容(url或者html5代码)")
    private String content;

    /**
     * 跳转方案(1:外部链接 2:H5界面 3:不跳转)
     */
    @ApiModelProperty("跳转方案(1:外部链接 2:H5界面 3:不跳转)")
    private Integer type;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private Integer sort;

    /**
     * 是否发布
     */
    @ApiModelProperty("是否发布")
    private Boolean publish;

    /**
     * 获取Banner图Id
     *
     * @return banner_id - Banner图Id
     */
    public String getBannerId() {
        return bannerId;
    }

    /**
     * 设置Banner图Id
     *
     * @param bannerId Banner图Id
     */
    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    /**
     * 获取Banner图名称
     *
     * @return banner_name - Banner图名称
     */
    public String getBannerName() {
        return bannerName;
    }

    /**
     * 设置Banner图名称
     *
     * @param bannerName Banner图名称
     */
    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    /**
     * 获取图片
     *
     * @return pic - 图片
     */
    public String getPic() {
        return pic;
    }

    /**
     * 设置图片
     *
     * @param pic 图片
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * 获取内容(url或者html5代码)
     *
     * @return content - 内容(url或者html5代码)
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容(url或者html5代码)
     *
     * @param content 内容(url或者html5代码)
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取跳转方案(1:外部链接 2:H5界面 3:不跳转)
     *
     * @return type - 跳转方案(1:外部链接 2:H5界面 3:不跳转)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置跳转方案(1:外部链接 2:H5界面 3:不跳转)
     *
     * @param type 跳转方案(1:外部链接 2:H5界面 3:不跳转)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取是否发布
     *
     * @return publish - 是否发布
     */
    public Boolean getPublish() {
        return publish;
    }

    /**
     * 设置是否发布
     *
     * @param publish 是否发布
     */
    public void setPublish(Boolean publish) {
        this.publish = publish;
    }
}