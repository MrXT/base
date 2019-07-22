package com.project.module.sys.controller;

import com.project.common.bean.LockUser;
import com.project.common.bean.excel.bean.ExcelSort;
import com.project.common.constant.SystemConstant;
import com.project.common.controller.BaseController;
import com.project.common.exception.BusinessException;
import com.project.common.spring.SessionHolder;
import com.project.common.util.CommonUtils;
import com.project.common.util.ValidateUtils;
import com.project.entity.User;
import com.project.module.sys.controller.vo.RoleVO;
import com.project.module.sys.controller.vo.UserDTO;
import com.project.module.sys.service.RoleService;
import com.project.module.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户的扩展控制器 ClassName: UserExtController
 *
 * @version 2017年2月7日
 */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BaseController<User, UserDTO> {

    /**
     * 用户serivce
     */
    @Autowired
    private UserService userService;

    /**
     * 角色service
     */
    @Autowired
    private RoleService roleService;


    /**
     * 后台页面管理员添加用户，与重置密码时使用的默认密码
     */
    @Value("${defaultPassword}")
    private String password;

    /**
     * 功能:跳转用户管理页面
     *
     * @param map
     * @param userVO
     * @return
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public String manage(final Map<String, Object> map, final UserDTO userVO) {
        SessionHolder.setMenuMap(map, "sys/user");
        // 获取当前用户的角色的子角色
        RoleVO roleVO = new RoleVO();
        roleVO.setValid(true);
        roleVO.setPage(true);
        // 查询角色列表
        map.put("roles", roleService.queryMapByCondition(roleVO).getList());
        return "sys/user/user_list";
    }

    /**
     * 跳转到管理员编辑页面
     *
     * @param map
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(final Map<String, Object> map, final UserDTO userDTO) {
        map.put("url", "sys/user");
        return "sys/user/user_edit";
    }

    /**
     * 跳转到用户详情页面
     *
     * @param map
     * @param userDTO
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail(final Map<String, Object> map, final UserDTO userDTO) {
        map.put("url", "sys/user");
        return "sys/user/user_detail";
    }

    /**
     * 功能：跳转管理员管理界面
     *
     * @param map
     * @param userDTO
     * @return
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/managePeople", method = RequestMethod.GET)
    public String managePeople(final Map<String, Object> map, final UserDTO userDTO) {
        SessionHolder.setMenuMap(map, "sys/user");
        // 获取当前用户的角色的子角色
        RoleVO roleVO = new RoleVO();
        roleVO.setValid(true);
        roleVO.setPage(true);
        // 查询所有的角色
        map.put("roles", roleService.queryMapByCondition(roleVO).getList());
        //当前用户id
        map.put("id", SessionHolder.getId());
        return "sys/user/usermanage_list";
    }

    /**
     * 功能:后台添加管理员
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Object insert(final UserDTO condition) {
        // 管理员的姓名，用户名，密码不能为空
        if (CommonUtils.hasBlank(condition.getName(), condition.getUsername(), condition.getPassword())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        // 新增的时候,随机一个盐值
        condition.setSalt(CommonUtils.get32UUID());
        // 设置用户的原始密码
        condition.setPass(condition.getPassword().toLowerCase());
        // 是否为app用户
        condition.setAppUser(false);
        // 设置验证密码（颜值+用户名+原始密码）
        condition.setPassword(CommonUtils.getPasswordBySalt(condition.getUsername(), condition.getPassword().toLowerCase(), condition.getSalt()));
        return userService.doInsert(condition);
    }

    /**
     * 功能:后台修改用户
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(final UserDTO condition) {
        // 防止恶意修改用户名，手机，密码，原始密码，支付密码，盐值
        condition.setUsername(null);
        condition.setPhone(null);
        condition.setPassword(null);
        condition.setPass(null);
        condition.setPayPass(null);
        condition.setSalt(null);
        //修改状态为冻结
        if (condition.getStatus() != null && condition.getStatus() == 3) {
            LockUser.add(condition.getUserId());
        }
        //修改状态为正常
        if (condition.getStatus() != null && condition.getStatus() == 2) {
            LockUser.remove(condition.getUserId());
        }
        return userService.doUpdate(condition);
    }

    /**
     * 根据条件分页与不分页查询查询用户信息（用与后台页面展示）
     *
     * @param condition
     * @return
     */
    @SuppressWarnings("deprecation")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    public Object query(final UserDTO condition) {
        // 查询用户的详情
        condition.setQueryDetail(true);
        return userService.queryMapByCondition(condition);
    }


    /**
     * 功能:导入用户信息，暂时不用
     *
     * @param condition
     * @param response
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(final UserDTO condition, final HttpServletResponse response) {
        List<ExcelSort> excelSorts = new ArrayList<ExcelSort>();
        excelSorts.add(new ExcelSort("用户名", "username"));
        excelSorts.add(new ExcelSort("姓名", "name"));
        userService.export(condition, response, excelSorts);
    }

    /**
     * 失效
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "/invalid", method = RequestMethod.POST)
    @ResponseBody
    public Object doInvalidate(final UserDTO condition) {
        if (ValidateUtils.isBlank(condition.getId())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        return userService.doInvalidate(condition);
    }

}