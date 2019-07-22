package com.project.module.cf.controller;

import com.project.common.constant.SystemConstant;
import com.project.common.controller.BaseController;
import com.project.common.exception.BusinessException;
import com.project.common.spring.SessionHolder;
import com.project.common.util.ValidateUtils;
import com.project.entity.CfCoinBuy;
import com.project.module.cf.controller.vo.CfCoinBuyVO;
import com.project.module.cf.service.CfCoinBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 参数购买配置控制器
 *
 * @version:2018-7-2
 */
@Controller
@RequestMapping("/cf/coinBuy")
public class CfCoinBuyController extends BaseController<CfCoinBuy, CfCoinBuyVO> {

    /**
     * 参数购买service
     */
    @Autowired
    private CfCoinBuyService cfCoinBuyService;

    /**
     * 功能:跳转金币购买配置页面
     *
     * @param cfCoinBuyVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(CfCoinBuyVO cfCoinBuyVO, Map<String, Object> map) {
        // 把增删改查权限放入map中
        SessionHolder.setMenuMap(map, "cf/coinBuy");
        return "cf/coinBuy/coinBuy_list";
    }

    /**
     * 功能:跳转金币购买配置页面
     *
     * @param cfCoinBuyVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(CfCoinBuyVO cfCoinBuyVO, Map<String, Object> map) {
        // 把增删改查权限放入map中
        map.put("url", "cf/coinBuy");
        return "cf/coinBuy/coinBuy_edit";
    }

    /**
     * 根据条件单表分页与不分页查询（默认不分页）
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(CfCoinBuyVO condition) {
        return cfCoinBuyService.queryByCondition(condition);
    }

    /**
     * 修改
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(CfCoinBuyVO condition) {
        if (ValidateUtils.isBlank(condition.getCoinBuyId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return cfCoinBuyService.doUpdate(condition);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Object insert(CfCoinBuyVO condition) {
        return cfCoinBuyService.doInsert(condition);
    }

    /**
     * 逻辑恢复-有效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/revalid", method = RequestMethod.POST)
    @ResponseBody
    public Object revalid(CfCoinBuyVO condition) {
        if (ValidateUtils.isBlank(condition.getCoinBuyId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return cfCoinBuyService.doRevalidate(condition);
    }

    /**
     * 逻辑删除-失效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    @ResponseBody
    public Object invalid(CfCoinBuyVO condition) {
        if (ValidateUtils.isBlank(condition.getCoinBuyId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return cfCoinBuyService.doInvalidate(condition);
    }
}