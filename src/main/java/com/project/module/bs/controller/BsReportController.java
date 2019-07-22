package com.project.module.bs.controller;

import com.project.common.constant.SystemConstant;
import com.project.common.controller.BaseController;
import com.project.common.exception.BusinessException;
import com.project.common.spring.SessionHolder;
import com.project.common.util.ValidateUtils;
import com.project.entity.BsReport;
import com.project.module.bs.controller.vo.BsReportVO;
import com.project.module.bs.service.BsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 举报工具类
 *
 * @version:2018-6-25
 */
@Controller
@RequestMapping("/bs/report")
public class BsReportController extends BaseController<BsReport, BsReportVO> {

    /**
     * 举报处理service
     */
    @Autowired
    private BsReportService bsReportService;

    /**
     * 功能:跳转到举报管理页面
     *
     * @param bsReportVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(BsReportVO bsReportVO, Map<String, Object> map) {
        // 把增删改查权限放入map中
        SessionHolder.setMenuMap(map, "bs/report");
        return "bs/report/report_list";
    }

    /**
     * 功能:跳转到举报管理页面
     *
     * @param bsReportVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(BsReportVO bsReportVO, Map<String, Object> map) {
        // 把增删改查权限放入map中
        map.put("url", "bs/report");
        return "bs/report/report_edit";
    }

    /**
     * 根据条件单表分页与不分页查询（默认不分页）
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(BsReportVO condition) {
        return bsReportService.queryByCondition(condition);
    }

    /**
     * 修改
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(BsReportVO condition) {
        if (ValidateUtils.isBlank(condition.getReportId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return bsReportService.doUpdate(condition);
    }

    /**
     * 逻辑删除-失效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    @ResponseBody
    public Object invalid(BsReportVO condition) {
        if (ValidateUtils.isBlank(condition.getReportId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return bsReportService.doInvalidate(condition);
    }
}