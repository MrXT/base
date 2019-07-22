package com.project.module.sys.service.impl;

import com.project.common.annotation.BaseAnnotation;
import com.project.common.bean.QueryResult;
import com.project.common.service.impl.BaseServiceImpl;
import com.project.entity.SysBanner;
import com.project.module.sys.controller.vo.SysBannerVO;
import com.project.module.sys.dao.SysBannerMapper;
import com.project.module.sys.service.SysBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Banner图处理实现类
 *
 * @version:2019-4-13
 */
@Service
public class SysBannerServiceImpl extends BaseServiceImpl<SysBanner, SysBannerVO> implements SysBannerService {
    /**
     * Banner图dao
     */
    @BaseAnnotation
    @Autowired
    private SysBannerMapper sysBannerMapper;

    /**
     * 重写查询方法
     *
     * @param condition
     * @return
     */
    @Override
    public QueryResult<SysBannerVO> queryByCondition(SysBannerVO condition) {
        QueryResult result = super.queryByCondition(condition);
        return result;
    }

    /**
     * 重写新增方法
     *
     * @param record
     * @return
     */
    @Transactional
    @Override
    public int doInsert(SysBannerVO record) {
        int result = super.doInsert(record);
        return result;
    }

    /**
     * 重写修改方法
     *
     * @param record
     * @return
     */
    @Transactional
    @Override
    public int doUpdate(SysBannerVO record) {
        int result = super.doUpdate(record);
        return result;
    }

    /**
     * 重写删除方法
     *
     * @param record
     * @return
     */
    @Transactional
    @Override
    public int doInvalidate(SysBannerVO record) {
        int result = super.doInvalidate(record);
        return result;
    }

}
