package com.project.module.sys.dao;

import com.project.common.util.MyMapper;
import com.project.entity.User;
import com.project.module.api.vo.ApiUserVO;
import com.project.module.sys.controller.vo.UserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserMapper extends MyMapper<User, UserDTO> {

    /**
     * 找到最大值
     *
     * @return
     */
    Long queryMaxCode();

    /**
     * 我的钱包
     *
     * @return
     */
    Map<String, Object> queryAccount(@Param("userId") String userId);

    /**
     * 根据用户id与金币数修改用户金币余额
     *
     * @param userId
     * @param coin
     * @return
     */
    int updateBalance(@Param("userId") String userId, @Param("coin") int coin);

    /**
     * 根据用户id与财富数修改用户财富值
     *
     * @param userId
     * @param integrate
     * @return
     */
    int updateIntegrate(@Param("userId") String userId, @Param("integrate") int integrate);

    /**
     * 根据用户id与金币数与财富值
     *
     * @param userId
     * @param coin
     * @param integrate
     * @return
     */
    int updateBalanceAndIntegrate(@Param("userId") String userId, @Param("coin") int coin, @Param("integrate") int integrate);

    /**
     * 查询用户详情
     *
     * @param userId 用户id
     * @param id     当前用户id
     * @return
     */
    ApiUserVO queryDetail(@Param("userId") String userId, @Param("currentId") String id);
}