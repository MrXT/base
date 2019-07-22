package com.project.module.sys.controller;

import com.project.common.bean.jpush.JPush;
import com.project.common.constant.SystemConstant;
import com.project.common.controller.BaseController;
import com.project.common.exception.BusinessException;
import com.project.common.spring.SessionHolder;
import com.project.common.util.CommonUtils;
import com.project.common.util.FastJsonUtils;
import com.project.common.util.ValidateUtils;
import com.project.entity.SysMessage;
import com.project.module.sys.controller.vo.SysMessageVO;
import com.project.module.sys.service.SysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 消息控制器
 *
 * @version:2017-10-24
 */
@Controller
@RequestMapping("/sys/message")
public class SysMessageController extends BaseController<SysMessage, SysMessageVO> {

    /**
     * 消息service
     */
    @Autowired
    private SysMessageService sysMessageService;

    /**
     * 极光推送工具
     */
    @Autowired
    private JPush jpush;

    /**
     * 功能:跳转消息管理页面
     *
     * @param sysMessageVO
     * @param map
     * @return
     */
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(final SysMessageVO sysMessageVO, final Map<String, Object> map) {
        // 把增删改查权限放入map中
        SessionHolder.setMenuMap(map, "sys/message");
        return "sys/message/message_list";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(final SysMessageVO sysMessageVO, final Map<String, Object> map) {
        map.put("url", "sys/message");
        return "sys/message/message_edit";
    }


    /**
     * 根据条件查询消息数据
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(final SysMessageVO condition) {
        return sysMessageService.queryByCondition(condition);
    }

    /**
     * 修改消息状态
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(final SysMessageVO condition) {
        //消息id不能为空
        if (ValidateUtils.isBlank(condition.getMessageId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return sysMessageService.doUpdate(condition);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Object insert(final SysMessageVO condition) {
        if (ValidateUtils.isBlank(condition.getMessageName())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        condition.setMessageId(CommonUtils.genId());
        condition.setDefaultValue();
        jpush.sendAllMsg(FastJsonUtils.toJSONString(condition));
        return 1;
    }

    /**
     * 逻辑恢复-有效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/revalid", method = RequestMethod.POST)
    @ResponseBody
    public Object revalid(final SysMessageVO condition) {
        if (ValidateUtils.isBlank(condition.getMessageId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return sysMessageService.doRevalidate(condition);
    }

    /**
     * 逻辑删除-失效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    @ResponseBody
    public Object invalid(final SysMessageVO condition) {
        if (ValidateUtils.isBlank(condition.getMessageId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return sysMessageService.doInvalidate(condition);
    }
}