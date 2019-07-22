package com.project.module.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.common.annotation.BaseAnnotation;
import com.project.common.bean.QueryResult;
import com.project.common.service.impl.BaseServiceImpl;
import com.project.entity.SysMessage;
import com.project.module.sys.controller.vo.SysMessageVO;
import com.project.module.sys.dao.SysMessageMapper;
import com.project.module.sys.service.SysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息处理实现类
 *
 * @version:2017-10-24
 */
@Service
public class SysMessageServiceImpl extends BaseServiceImpl<SysMessage, SysMessageVO> implements SysMessageService {

    /**
     * 消息dao
     */
    @BaseAnnotation
    @Autowired
    private SysMessageMapper sysMessageMapper;

    /**
     * 重写queryByCondition方法
     *
     * @param condition
     * @return
     */
    @Override
    public QueryResult<SysMessageVO> queryByCondition(SysMessageVO condition) {
        //默认安装utime （修改时间）降序
        PageHelper.startPage(condition.getPageNo(), condition.getPageSize(), "utime desc");
        List<SysMessageVO> list = sysMessageMapper.queryDataByCondition(condition);
        return new QueryResult<SysMessageVO>(new PageInfo<SysMessageVO>(list));
    }
}
