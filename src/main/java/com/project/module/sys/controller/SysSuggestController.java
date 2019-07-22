package com.project.module.sys.controller;

import com.project.common.constant.SystemConstant;
import com.project.common.controller.BaseController;
import com.project.common.exception.BusinessException;
import com.project.common.spring.SessionHolder;
import com.project.common.util.ValidateUtils;
import com.project.entity.SysSuggest;
import com.project.module.sys.controller.vo.SysSuggestVO;
import com.project.module.sys.service.SysSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 意见反馈控制器
 *
 * @version:2018-6-6
 */
@Controller
@RequestMapping("/sys/suggest")
public class SysSuggestController extends BaseController<SysSuggest, SysSuggestVO> {

    /**
     * 意见反馈service
     */
    @Autowired
    private SysSuggestService sysSuggestService;

    /**
     * 功能:跳转意见反馈列表页面
     *
     * @param sysSuggestVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(SysSuggestVO sysSuggestVO, Map<String, Object> map) {
        // 把增删改查权限放入map中
        SessionHolder.setMenuMap(map, "sys/suggest");
        return "sys/suggest/suggest_list";
    }

    /**
     * 功能:跳转意见反馈列表页面
     *
     * @param sysSuggestVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(SysSuggestVO sysSuggestVO, Map<String, Object> map) {
        map.put("url", "sys/suggest");
        return "sys/suggest/suggest_edit";
    }

    /**
     * 根据条件单表分页与不分页查询（默认不分页）
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(SysSuggestVO condition) {// 在接口文档界面上忽略该参数
        return sysSuggestService.queryByCondition(condition);
    }

    /**
     * 修改
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(SysSuggestVO condition) {
        if (ValidateUtils.isBlank(condition.getSuggestId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return sysSuggestService.doUpdate(condition);
    }

    /**
     * 验证手机号码
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((16[0-9])|(19[0-9])|(17[0-9])|(13[0-9])|(14[0-9])|(15([0-9]))|(18[0-9]))\\d{8}$";
        return check(cellphone, regex);
    }

    /**
     * 功能:正则匹配
     *
     * @param str
     * @param regex
     * @return
     */
    public static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 逻辑恢复-有效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/revalid", method = RequestMethod.POST)
    @ResponseBody
    public Object revalid(SysSuggestVO condition) {
        if (ValidateUtils.isBlank(condition.getSuggestId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return sysSuggestService.doRevalidate(condition);
    }

    /**
     * 逻辑删除-失效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    @ResponseBody
    public Object invalid(SysSuggestVO condition) {
        if (ValidateUtils.isBlank(condition.getSuggestId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return sysSuggestService.doInvalidate(condition);
    }
}