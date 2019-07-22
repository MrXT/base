package com.project.module.bs.service.impl;

import com.project.common.annotation.BaseAnnotation;
import com.project.common.service.impl.BaseServiceImpl;
import com.project.entity.BsRecharge;
import com.project.module.bs.controller.vo.BsRechargeVO;
import com.project.module.bs.dao.BsRechargeMapper;
import com.project.module.bs.service.BsRechargeService;
import com.project.module.sys.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

/**
 * 充值处理实现类
 *
 * @version:2018-6-29
 */
@Service
public class BsRechargeServiceImpl extends BaseServiceImpl<BsRecharge, BsRechargeVO> implements BsRechargeService {

    /**
     * 充值dao
     */
    @BaseAnnotation
    @Autowired
    private BsRechargeMapper bsRechargeMapper;

    /**
     * 用户dao
     */
    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public int doUpdate(BsRechargeVO record) {
        //防止多次推送消息
        BsRecharge recharge1 = bsRechargeMapper.selectByPrimaryKey(record.getRechargeId());
        if (recharge1 != null && recharge1.getValid()) {
            return 1;
        }
        // 先修改充值记录为有效
        int result = super.doUpdate(record);
        if (result == 1) {
            // 修改成功，查询记录的金币数
            BsRecharge recharge = bsRechargeMapper.selectByPrimaryKey(record.getRechargeId());
            int coin = recharge.getCoin();
            String userId = recharge.getUserId();
            // 修改用户的金币余额
            return userMapper.updateBalance(userId, coin);
        }
        return result;
    }

    @Transactional
    @Override
    public int doInsert(BsRechargeVO record) {
        int result = super.doInsert(record);
        // 如果是内购的就直接修改金币余额
        if (result == 1 && record.getPayType() == 3) {
            int coin = record.getCoin();
            String userId = record.getUserId();
            // 修改用户的金币余额
            return userMapper.updateBalance(userId, coin);
        }
        return result;
    }

    @Override
    public int doInsertRecord(BsRechargeVO rechargeVO) {
        return super.doInsert(rechargeVO);
    }
}
