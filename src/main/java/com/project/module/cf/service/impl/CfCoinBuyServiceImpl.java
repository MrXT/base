package com.project.module.cf.service.impl;

import com.project.common.annotation.BaseAnnotation;
import com.project.common.service.impl.BaseServiceImpl;
import com.project.entity.CfCoinBuy;
import com.project.module.cf.controller.vo.CfCoinBuyVO;
import com.project.module.cf.dao.CfCoinBuyMapper;
import com.project.module.cf.service.CfCoinBuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 金币购买配置处理实现类
 *
 * @version:2018-7-2
 */
@Service
public class CfCoinBuyServiceImpl extends BaseServiceImpl<CfCoinBuy, CfCoinBuyVO> implements CfCoinBuyService {

    /**
     * 金币购买配置dao
     */
    @BaseAnnotation
    @Autowired
    private CfCoinBuyMapper cfCoinBuyMapper;
}
