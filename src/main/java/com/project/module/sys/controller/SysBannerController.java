package com.project.module.sys.controller;

import com.project.common.controller.BaseController;
import com.project.common.spring.SessionHolder;
import com.project.entity.SysBanner;
import com.project.module.sys.controller.vo.SysBannerVO;
import com.project.module.sys.service.SysBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Banner图控制类
 *
 * @version:2019-4-13
 */
@Controller
@RequestMapping("/sys/banner")
public class SysBannerController extends BaseController<SysBanner, SysBannerVO> {
    /**
     * Banner图service
     */
    @Autowired
    private SysBannerService sysBannerService;

    /**
     * 功能:跳转到Banner图管理页面
     *
     * @param sysBannerVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(SysBannerVO sysBannerVO, Map<String, Object> map) {
        //把增删改查权限放入map中
        SessionHolder.setMenuMap(map, "sys/banner");
        return "sys/banner/banner_list";
    }

    /**
     * 功能:跳转到Banner图编辑页面
     *
     * @param sysBannerVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(SysBannerVO sysBannerVO, Map<String, Object> map) {
        map.put("url", "sys/banner");
        return "sys/banner/banner_edit";
    }

    /**
     * 根据相关的条件查询Banner图数据，后台专用
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(SysBannerVO condition) {
        return sysBannerService.queryByCondition(condition);
    }

    /**
     * 修改
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(SysBannerVO condition) {
        return sysBannerService.doUpdate(condition);
    }

    /**
     * 新增
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Object insert(SysBannerVO condition) {
        return sysBannerService.doInsert(condition);
    }


    /**
     * 逻辑删除-失效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    @ResponseBody
    public Object invalid(SysBannerVO condition) {
        return sysBannerService.doInvalidate(condition);
    }

}