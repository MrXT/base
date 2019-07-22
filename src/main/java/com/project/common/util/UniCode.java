package com.project.common.util;

import com.project.module.sys.dao.UserMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成唯一的userid编码 ClassName: UniCode <br/>
 *
 * @version 2018年4月17日
 */
public class UniCode {

    /**
     * id初始值未100000
     */
    private static Long START_CODE = 100000l;

    private static int[] rands = {1, 3, 5};

    static {
        // 查询用户当前最大的id值
        UserMapper userMapper = SpringContextUtils.getBean(UserMapper.class);
        Long max = userMapper.queryMaxCode();
        if (max != null && max > START_CODE) {
            START_CODE = max;
        }

    }

    /**
     * 功能:. <br/>
     *
     * @param num 生成唯一用户的数量
     * @return
     */
    public static List<String> generator(int num) {
        List<String> result = new ArrayList<String>();
        // 同步代码块
        synchronized (START_CODE) {
            for (int i = 1; i <= num; i++) {
                int r = (int) (Math.random() * 3);
                int ran = rands[r];
                START_CODE += ran;
                result.add(String.valueOf(START_CODE));
            }
        }
        return result;
    }

}