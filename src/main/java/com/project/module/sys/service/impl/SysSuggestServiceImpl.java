package com.project.module.sys.service.impl;

import com.project.common.annotation.BaseAnnotation;
import com.project.common.service.impl.BaseServiceImpl;
import com.project.entity.SysSuggest;
import com.project.module.sys.controller.vo.SysSuggestVO;
import com.project.module.sys.dao.SysSuggestMapper;
import com.project.module.sys.service.SysSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 意见反馈处理实现类
 *
 * @version:2018-6-6
 */
@Service
public class SysSuggestServiceImpl extends BaseServiceImpl<SysSuggest, SysSuggestVO> implements SysSuggestService {

    /**
     * 意见反馈dao
     */
    @BaseAnnotation
    @Autowired
    private SysSuggestMapper sysSuggestMapper;
}
