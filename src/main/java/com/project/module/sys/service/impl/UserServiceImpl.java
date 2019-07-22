package com.project.module.sys.service.impl;

import com.project.common.annotation.BaseAnnotation;
import com.project.common.bean.LockUser;
import com.project.common.bean.LoginUser;
import com.project.common.bean.QueryResult;
import com.project.common.bean.excel.bean.ExcelSort;
import com.project.common.constant.SystemConstant;
import com.project.common.exception.BusinessException;
import com.project.common.service.impl.BaseServiceImpl;
import com.project.common.spring.SessionHolder;
import com.project.common.util.*;
import com.project.entity.Menu;
import com.project.entity.SysLoginLog;
import com.project.entity.User;
import com.project.module.api.vo.ApiUserVO;
import com.project.module.bs.dao.BsFeeMapper;
import com.project.module.bs.dao.BsRechargeMapper;
import com.project.module.sys.controller.vo.UserDTO;
import com.project.module.sys.controller.vo.UserVO;
import com.project.module.sys.dao.*;
import com.project.module.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 用户处理实现类
 *
 * @version:2017-1-2
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserDTO> implements UserService {

    /**
     * 用户dao
     */
    @BaseAnnotation
    @Autowired
    private UserMapper userMapper;


    /**
     * 用户角色dao
     */
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 消费dao
     */
    @Autowired
    private BsFeeMapper bsFeeMapper;

    /**
     * 角色菜单dao
     */
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 菜单dao
     */
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 充值dao
     */
    @Autowired
    private BsRechargeMapper bsRechargeMapper;

    /**
     * 登录日志dao
     */
    @Autowired
    private SysLoginLogMapper sysLoginLogMapper;

    /**
     * admin账户
     */
    @Value("${admin}")
    private String admin;

    @Value("${pass}")
    private Boolean pass;

    @Value("${salt}")
    private String salt;

    @Value("${adminPassword}")
    private String adminPassword;


    /**
     * 根据条件查询用户
     *
     * @param condition
     * @return
     */
    @Override
    public QueryResult<Map<String, Object>> queryMapByCondition(final UserDTO condition) {
        QueryResult<Map<String, Object>> result = super.queryMapByCondition(condition);
        // 获取用户id数组
        List<String> userIds = MapUtils.getValuesByListMapAndKey(result.getList(), "userId");
        if (!ListUtils.isEmpty(userIds)) {
            UserVO queryUser = new UserVO();
            queryUser.setIds(userIds);
            // 查询有效的角色
            queryUser.setValid(true);
            List<Map<String, Object>> roles = userRoleMapper.queryRoleByUser(queryUser);
            MapUtils.insertDataToResult(result.getList(), roles, "userId", "roles");
            result.getList().forEach(stringObjectMap -> {
                // 去除密码相关信息
                stringObjectMap.remove("pass");
                stringObjectMap.remove("password");
            });
        }
        return result;
    }

    /**
     * 修改用户信息
     *
     * @param record
     * @return
     */
    @Transactional
    @Override
    public int doUpdate(UserDTO record) {
        if (ValidateUtils.isNotBlank(record.getPhone())) {
            User current = userMapper.selectByPrimaryKey(record.getUserId());
            if (ValidateUtils.isBlank(current.getPhone()) || !CommonUtils.compareString(current.getPhone(), record.getPhone())) {
                User user = ObjectUtil.generotorValid(new User());
                user.setPhone(record.getPhone());
                // 查询手机号是否已被绑定
                if (userMapper.select(user).size() > 0) {
                    throw new BusinessException("该手机号已被其他账号绑定！");
                }
            }
        }
        int result = super.doUpdate(record);
        if (result == 1 && ValidateUtils.isNotBlank(record.getHeadUrl())) {
            LoginUser user = SessionHolder.getUser();
            user.setHeadUrl(record.getHeadUrl());
            SessionHolder.setUser(user);
        }
        return result;
    }

    /**
     * 后台新增用户或者管理员
     *
     * @param record
     * @return
     */
    @Transactional
    @Override
    public int doInsert(final UserDTO record) {
        User user = new User();
        user.setUsername(record.getUsername());
        user.setValid(true);
        // 查询当前账号或者手机号是否存在
        if (!ListUtils.isEmpty(userMapper.select(user)) || CommonUtils.compareString(record.getUsername(), admin)) {
            throw new BusinessException("请更换账号，系统已有该账号！");
        }
        record.setUserId(UniCode.generator(1).get(0));
        // 添加用户
        int result = super.doInsert(record);
        if (result == 1) {
        }
        return result;
    }

    /**
     * 查询非当前用户详情
     *
     * @param apiUser
     * @return
     */
    @Override
    public ApiUserVO queryUserDetail(ApiUserVO apiUser) {
        ApiUserVO result = userMapper.queryDetail(apiUser.getUserId(), SessionHolder.getId());
        return result;
    }

    /**
     * 查询当前用户详情
     *
     * @return
     */
    @Override
    public Object queryDetail(String userId) {
        UserDTO condition = new UserDTO();
        condition.setUserId(ValidateUtils.isBlank(userId) ? SessionHolder.getId() : userId);
        // 查询用户数据
        List<Map<String, Object>> users = userMapper.queryMapByCondition(condition);
        if (ListUtils.isEmpty(users)) {
            throw new BusinessException(SystemConstant.NULL_OBJECT);
        }
        // 计算是否有密码
        if (users.get(0).get("password") != null) {
            users.get(0).put("havePass", true);
        } else {
            users.get(0).put("havePass", false);
        }
        // 计算有没有支付密码
        if (users.get(0).get("payPass") != null) {
            users.get(0).put("havePayPass", true);
        } else {
            users.get(0).put("havePayPass", false);
        }
        /**
         * 删除password字段
         */
        users.get(0).remove("password");
        users.get(0).remove("pass");
        users.get(0).remove("payPass");
        return users.get(0);
    }

    /**
     * 数据导出
     *
     * @param condition
     * @param response
     * @param excelSorts
     */
    @Override
    public void export(final UserDTO condition, final HttpServletResponse response, final List<ExcelSort> excelSorts) {
        List<Map<String, Object>> maps = queryMapByCondition(condition).getList();
        ExcelUtils.export(maps, excelSorts, DateUtils.toMailDateString(new Date()), response);
    }

    /**
     * 更换手机号
     *
     * @param oldPhone
     * @param newPhone
     * @return
     */
    @Transactional
    @Override
    public Object doUpdatePhone(String oldPhone, String newPhone) {
        User user = new User();
        user.setUsername(newPhone);
        user.setPhone(newPhone);
        // 查询手机号是否已被绑定
        if (userMapper.select(user).size() > 0) {
            throw new BusinessException("该手机号已被其他账号绑定！");
        }
        Integer result = 0;
        User condition = new User();
        condition.setUserId(SessionHolder.getId());
        condition.setPhone(newPhone);
        condition.setUsername(newPhone);
        // 查询出当前用户
        User oldUser = userMapper.selectByPrimaryKey(SessionHolder.getId());
        condition.setSalt(oldUser.getSalt());
        // 修改密码
        if (ValidateUtils.isNotBlank(oldUser.getPass())) {
            // 密码规则
            condition.setPassword(CommonUtils.getPasswordBySalt(condition.getUsername(), oldUser.getPass(), condition.getSalt()));
        }
        result = userMapper.updateByPrimaryKeySelective(condition);
        // 刷新session里面的数据
        LoginUser loginUser = SessionHolder.getUser();
        loginUser.setPhone(newPhone);
        loginUser.setUsername(newPhone);
        if (ValidateUtils.isNotBlank(oldUser.getPass())) {
            loginUser.setPassword(condition.getPassword());
        }
        SessionHolder.setUser(loginUser);
        return result;
    }

    /**
     * 第三方登录
     *
     * @param condition  条件
     * @param resultUser 查询
     * @return
     */
    @Transactional
    @Override
    public String threeByLogin(UserDTO condition, UserVO resultUser) {
        List<User> users = userMapper.select(resultUser);
        if (!ListUtils.isEmpty(users)) {
            //如果用户存在
            resultUser = FastJsonUtils.beanToBean(users.get(0), UserVO.class);
        } else {
            // 没有就插入
            condition.setUserId(UniCode.generator(1).get(0));
            //没有name，默认用户+ID
            if (ValidateUtils.isBlank(condition.getName())) {
                condition.setName("用户" + condition.getUserId());
            }
            //默认20岁
            Date birthDate = DateUtils.addYear(new Date(), -20);
            condition.setBirth(DateUtils.toShortDateString(birthDate));
            super.doInsert(condition);
            resultUser = condition;
        }
        // 做登录操作
        login(resultUser);
        return resultUser.getUserId();
    }

    /**
     * app登录
     *
     * @param user
     * @return
     */
    @Override
    public LoginUser queryUserByCondition(final UserVO user) {
        UserVO resultUser = new UserVO();
        if (CommonUtils.compareString(user.getUsername(), admin, true)) {// 如果是超级管理员
            resultUser.setUsername(admin);
            resultUser.setName(admin);
            resultUser.setPassword(adminPassword);
            resultUser.setValid(true);
            resultUser.setSalt(salt);// 查询有效的角色，有效的菜单
        } else {
            UserVO queryUser = new UserVO();
            queryUser.setUsername(user.getUsername());
            List<User> users = userMapper.select(queryUser);
            if (ListUtils.isEmpty(users)) {
                throw new BusinessException("该用户不存在！");
            }
            if (!(users.get(0).getValid())) {
                throw new BusinessException("该用户已失效，请联系管理员！");
            }
            resultUser = FastJsonUtils.beanToBean(users.get(0), UserVO.class);
        }
        if (!CommonUtils.checkLoginPassword(resultUser.getPassword(), user.getUsername(), user.getPassword(), resultUser.getSalt())) {
            throw new BusinessException("你输入的账号或密码有误！");
        }
        return login(resultUser);
    }

    /**
     * app注册登录
     *
     * @param condition
     * @return
     */
    @Transactional
    @Override
    public LoginUser doInsertAndLogin(UserDTO condition) {
        User user = new User();
        if (ValidateUtils.isNotBlank(condition.getEmail())) {
            user.setEmail(condition.getEmail());
        }
        if (ValidateUtils.isNotBlank(condition.getPhone())) {
            user.setPhone(condition.getPhone());
        }
        user.setValid(true);
        // 查询当前账号或者手机号是否存在
        if (!ListUtils.isEmpty(userMapper.select(user))) {
            throw new BusinessException("该账号已被注册！");
        }
        condition.setUserId(UniCode.generator(1).get(0));
        //没有name，默认用户+ID
        if (ValidateUtils.isBlank(condition.getName())) {
            condition.setName("用户" + condition.getUserId());
        }
        //默认20岁
        Date birthDate = DateUtils.addYear(new Date(), -20);
        condition.setBirth(DateUtils.toShortDateString(birthDate));
        // 添加用户
        int result = super.doInsert(condition);

        LoginUser loginUser = null;
        if (result == 1) {
            // 做登录操作
            loginUser = login(condition);
        } else {
            throw new BusinessException("注册失败");
        }
        return loginUser;
    }

    /**
     * 做登录操作
     *
     * @param resultUser
     */
    private LoginUser login(UserVO resultUser) {
        //封装登录用户类
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(resultUser.getUsername());
        loginUser.setName(resultUser.getName());
        loginUser.setPhone(resultUser.getPhone());
        loginUser.setHeadUrl(resultUser.getHeadUrl());
        loginUser.setPass(resultUser.getPass());
        loginUser.setSalt(resultUser.getSalt());
        loginUser.setPassword(resultUser.getPassword());
        loginUser.setPayPass(resultUser.getPayPass());

        //修改菜单
        resultUser.setMenus(roleMenuMapper.queryMenuByUser(resultUser));
        List<String> passUrls = new ArrayList<String>();
        for (Menu menu : resultUser.getMenus()) {
            passUrls.add(menu.getMenuUrl());
        }
        loginUser.setPassUrls(passUrls);
        loginUser.setId(resultUser.getUserId());
        if (ValidateUtils.isBlank(resultUser.getUserId())) {
            loginUser.setId(SessionHolder.getAdminId());
        }
        if(LockUser.userIds.contains(loginUser.getId())){
            throw new BusinessException(203, "该用户被锁定，请联系管理员");
        }
        SessionHolder.setUser(loginUser);
        //插入登录日志
        sysLoginLogMapper.insertSelective(new SysLoginLog(loginUser.getId(), new Date()));
        return loginUser;
    }

    /**
     * 我的钱包
     *
     * @return
     */
    @Override
    public Object queryAccount() {
        Map<String, Object> map = userMapper.queryAccount(SessionHolder.getId());
        return map;
    }

    /**
     * 修改金币
     *
     * @param userId
     * @param balance
     * @return
     */
    @Override
    public int updateBalance(String userId, int balance) {
        return userMapper.updateBalance(userId, balance);
    }

    /**
     * 首页数据统计
     *
     * @return
     */
    @Override
    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>();
        return result;
    }

}
