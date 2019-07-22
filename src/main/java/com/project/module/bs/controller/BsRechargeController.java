package com.project.module.bs.controller;

import com.project.common.constant.SystemConstant;
import com.project.common.controller.BaseController;
import com.project.common.exception.BusinessException;
import com.project.common.spring.SessionHolder;
import com.project.common.util.ValidateUtils;
import com.project.entity.BsRecharge;
import com.project.module.bs.controller.vo.BsRechargeVO;
import com.project.module.bs.service.BsRechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 充值控制器
 *
 * @version:2018-6-29
 */
@Controller
@RequestMapping("/bs/recharge")
public class BsRechargeController extends BaseController<BsRecharge, BsRechargeVO> {

    /**
     * 充值处理service
     */
    @Autowired
    private BsRechargeService bsRechargeService;

    /**
     * 功能:跳转到充值管理页面
     *
     * @param bsRechargeVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(BsRechargeVO bsRechargeVO, Map<String, Object> map) {
        // 把增删改查权限放入map中
        SessionHolder.setMenuMap(map, "bs/recharge");
        return "bs/recharge/recharge_list";
    }

    /**
     * 根据条件单表分页与不分页查询（默认不分页）
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(BsRechargeVO condition) {
        return bsRechargeService.queryByCondition(condition);
    }


    /**
     * 逻辑删除-失效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    @ResponseBody
    public Object invalid(BsRechargeVO condition) {
        if (ValidateUtils.isBlank(condition.getRechargeId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return bsRechargeService.doInvalidate(condition);
    }
}