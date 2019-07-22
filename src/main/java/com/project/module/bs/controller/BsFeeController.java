package com.project.module.bs.controller;

import com.project.common.constant.SystemConstant;
import com.project.common.controller.BaseController;
import com.project.common.exception.BusinessException;
import com.project.common.spring.SessionHolder;
import com.project.common.util.ValidateUtils;
import com.project.entity.BsFee;
import com.project.module.bs.controller.vo.BsFeeVO;
import com.project.module.bs.service.BsFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 消费控制器
 *
 * @version:2018-6-29
 */
@Controller
@RequestMapping("/bs/fee")
public class BsFeeController extends BaseController<BsFee, BsFeeVO> {

    /**
     * 消费处理service
     */
    @Autowired
    private BsFeeService bsFeeService;

    /**
     * 功能:跳转消费管理页面
     *
     * @param bsFeeVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(BsFeeVO bsFeeVO, Map<String, Object> map) {
        // 把增删改查权限放入map中
        SessionHolder.setMenuMap(map, "bs/fee");
        return "bs/fee/fee_list";
    }

    /**
     * 根据条件单表分页与不分页查询消费信息（默认不分页）
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(BsFeeVO condition) {
        return bsFeeService.queryByCondition(condition);
    }

    /**
     * 逻辑删除-失效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    @ResponseBody
    public Object invalid(BsFeeVO condition) {
        if (ValidateUtils.isBlank(condition.getFeeId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return bsFeeService.doInvalidate(condition);
    }
}