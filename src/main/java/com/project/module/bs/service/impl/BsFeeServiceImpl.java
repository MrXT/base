package com.project.module.bs.service.impl;

import com.project.common.annotation.BaseAnnotation;
import com.project.common.exception.BusinessException;
import com.project.common.service.impl.BaseServiceImpl;
import com.project.entity.BsFee;
import com.project.module.bs.controller.vo.BsFeeVO;
import com.project.module.bs.dao.BsFeeMapper;
import com.project.module.bs.service.BsFeeService;
import com.project.module.sys.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * 消费处理实现类
 *
 * @version:2018-6-29
 */
@Service
public class BsFeeServiceImpl extends BaseServiceImpl<BsFee, BsFeeVO> implements BsFeeService {

    /**
     * 消费dao
     */
    @BaseAnnotation
    @Autowired
    private BsFeeMapper bsFeeMapper;

    /**
     * 用户dao
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取当前季度的开始时间
     *
     * @return
     */
    public Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        Date now = null;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            now = c.getTime();
        } catch (Exception e) {
            throw new BusinessException("查询失败");
        }
        return now;
    }

    /**
     * 获得指定日期对应本年度第一天的日期
     *
     * @param aDate 日期
     * @return
     */
    public Date getYearStartDate(Date aDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        cal.set(Calendar.MONTH, 0);// 1月
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }


}
