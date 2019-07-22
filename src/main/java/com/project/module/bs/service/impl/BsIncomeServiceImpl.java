package com.project.module.bs.service.impl;

import com.project.common.annotation.BaseAnnotation;
import com.project.common.service.impl.BaseServiceImpl;
import com.project.entity.BsIncome;
import com.project.module.bs.controller.vo.BsIncomeVO;
import com.project.module.bs.dao.BsIncomeMapper;
import com.project.module.bs.service.BsIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 收益处理实现类
 *
 * @version:2018-7-13
 */
@Service
public class BsIncomeServiceImpl extends BaseServiceImpl<BsIncome, BsIncomeVO> implements BsIncomeService {

    /**
     * 收益dao
     */
    @BaseAnnotation
    @Autowired
    private BsIncomeMapper bsIncomeMapper;
}
