package com.project.module.bs.service.impl;

import com.project.common.annotation.BaseAnnotation;
import com.project.common.service.impl.BaseServiceImpl;
import com.project.entity.BsReport;
import com.project.module.bs.controller.vo.BsReportVO;
import com.project.module.bs.dao.BsReportMapper;
import com.project.module.bs.service.BsReportService;
import com.project.module.sys.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 举报处理实现类
 *
 * @version:2018-6-25
 */
@Service
public class BsReportServiceImpl extends BaseServiceImpl<BsReport, BsReportVO> implements BsReportService {

    /**
     * 举报dao
     */
    @BaseAnnotation
    @Autowired
    private BsReportMapper bsReportMapper;


    /**
     * 用户dao
     */
    @Autowired
    private UserMapper userMapper;

    @Override
    public int doUpdate(BsReportVO record) {
        //处理投诉
        if (record.getStatus() != null && record.getStatus()) {
        }
        return super.doUpdate(record);
    }
}
