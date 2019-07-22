package com.project.module.sys.service;

import com.project.common.bean.LoginUser;
import com.project.common.service.BaseService;
import com.project.entity.User;
import com.project.module.api.vo.ApiUserVO;
import com.project.module.sys.controller.vo.UserDTO;
import com.project.module.sys.controller.vo.UserVO;

import java.util.Map;

/**
 * @author xt
 * @version:2017-2-7
 */
public interface UserService extends BaseService<User, UserDTO> {

    /**
     * 查询当前登录用户详情
     *
     * @return
     */
    Object queryDetail(String userId);

    /**
     * 绑定-更换手机
     *
     * @param oldPhone
     * @param newPhone
     * @return
     */
    Object doUpdatePhone(String oldPhone, String newPhone);

    /**
     * 用户第三方注册登录
     *
     * @param condition
     * @param resultUser 查询
     * @return
     */
    String threeByLogin(UserDTO condition,UserVO resultUser);

    /**
     * 登录使用
     *
     * @param user
     * @return
     */
    LoginUser queryUserByCondition(UserVO user);


    /**
     * 查询当前登录用户钱包
     *
     * @return
     */
    Object queryAccount();

    /**
     * 修改用户金币余额
     *
     * @param userId
     * @param balance
     * @return
     */
    int updateBalance(String userId, int balance);

    /**
     * 查询其他用户详情
     *
     * @param apiUser
     * @return
     */
    ApiUserVO queryUserDetail(ApiUserVO apiUser);

    /**
     * 注册并登录
     *
     * @param condition
     * @return
     */
    LoginUser doInsertAndLogin(UserDTO condition);

    /**
     * 查询首页统计信息
     *
     * @return
     */
    Map<String, Object> getAll();
}