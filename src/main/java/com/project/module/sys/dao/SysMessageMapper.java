package com.project.module.sys.dao;

import com.project.common.util.MyMapper;
import com.project.entity.SysMessage;
import com.project.module.sys.controller.vo.SysMessageVO;

import java.util.List;

/**
 * 消息数据处理
 * ClassName: SysMessageMapper <br/>
 *
 * @version 2018年7月25日
 */
public interface SysMessageMapper extends MyMapper<SysMessage, SysMessageVO> {

    /**
     * 根据扩展条件来查询数据库返回消息
     *
     * @return
     * @ram condition
     */
    List<SysMessageVO> queryDataByCondition(SysMessage condition);
}