package com.project.module.bs.service;

import com.project.common.service.BaseService;
import com.project.entity.BsRecharge;
import com.project.module.bs.controller.vo.BsRechargeVO;

/**
 * 充值处理类
 *
 * @version:2018-6-29
 */
public interface BsRechargeService extends BaseService<BsRecharge, BsRechargeVO> {

    int doInsertRecord(BsRechargeVO rechargeVO);
}