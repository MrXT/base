package com.project.module.bs.controller;

import com.project.common.controller.BaseController;
import com.project.common.spring.SessionHolder;
import com.project.entity.BsIncome;
import com.project.module.bs.controller.vo.BsIncomeVO;
import com.project.module.bs.service.BsIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 收益控制器
 *
 * @version:2018-7-13
 */
@Controller
@RequestMapping("/bs/income")
public class BsIncomeController extends BaseController<BsIncome, BsIncomeVO> {

    @Autowired
    private BsIncomeService bsIncomeService;

    /**
     * 功能:跳转页面（暂时没用）
     *
     * @param bsIncomeVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(BsIncomeVO bsIncomeVO, Map<String, Object> map) {
        // 把增删改查权限放入map中
        SessionHolder.setMenuMap(map, "bs/income");
        return "bs/income/income_list";
    }

    /**
     * 根据条件单表分页与不分页查询收益信息（默认不分页）
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(BsIncomeVO condition) {
        return bsIncomeService.queryByCondition(condition);
    }

}