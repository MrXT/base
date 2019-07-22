package com.project.common.controller;

import com.project.common.annotation.ApiResponseDetail;
import com.project.common.bean.Cache;
import com.project.common.bean.LoginUser;
import com.project.common.bean.ResponseJson;
import com.project.common.constant.MenuTypeEnum;
import com.project.common.constant.SystemConstant;
import com.project.common.exception.BusinessException;
import com.project.common.spring.SessionHolder;
import com.project.common.util.*;
import com.project.entity.Menu;
import com.project.module.sys.controller.vo.MenuVO;
import com.project.module.sys.controller.vo.UserDTO;
import com.project.module.sys.controller.vo.UserVO;
import com.project.module.sys.service.MenuService;
import com.project.module.sys.service.UserExtService;
import com.project.module.sys.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * 登录使用 ClassName: LoginController <br/>
 *
 * @author xt(di.xiao @ karakal.com.cn)
 * @version 1.0
 * @date 2017年1月20日
 */
@RequestMapping("login")
@Controller
@Api(value = "2.1 登录接口", description = "登录,修改密码,获取验证码")
public class LoginController {

    /**
     * 用户扩展service
     */
    @Autowired
    private UserExtService userExtService;

    /**
     * 用户service
     */
    @Autowired
    private UserService userService;

    /**
     * 菜单service
     */
    @Autowired
    private MenuService menuService;

    /**
     * 缓存
     */
    @Autowired
    private Cache cache;

    /**
     * 主页面
     *
     * @return
     */
    @RequestMapping("/index")
    public String index(final Model map) {
        return main(map);
    }

    /**
     * 跳转登录页面
     *
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 跳转主页面
     *
     * @return
     */
    @RequestMapping("/main")
    public String main(final Model map) {
        //验证token
        if (!SessionHolder.validToken()) {
            return login();
        }
        //获取当前用户
        LoginUser user = SessionHolder.getUser();
        if (user == null) {
            return login();
        }
        List<MenuVO> result = new ArrayList<MenuVO>();
        List<Menu> menus = new ArrayList<Menu>();
        //查询用户菜单
        List<Menu> userMenus = menuService.queryMenuByUser(user.getId());
        for (Menu menu : userMenus) {
            // 显示未隐藏的菜单
            if (!menu.getHidden()) {
                if (ValidateUtils.isBlank(menu.getParentId())) {
                    MenuVO menuVo = FastJsonUtils.toBean(FastJsonUtils.toJSONString(menu), MenuVO.class);
                    result.add(menuVo);
                }
                if (menu.getMenuType() == MenuTypeEnum.DIR.getValue()) {
                    menus.add(menu);
                }
            }
        }
        //按照menuOrder升序
        Collections.sort(result, new Comparator<MenuVO>() {
            @Override
            public int compare(final MenuVO o1, final MenuVO o2) {
                return o1.getMenuOrder() - o2.getMenuOrder();
            }
        });
        //按照menuOrder升序
        Collections.sort(menus, new Comparator<Menu>() {
            @Override
            public int compare(final Menu o1, final Menu o2) {
                return o1.getMenuOrder() - o2.getMenuOrder();
            }
        });
        //获取树状菜单
        menuService.getTree(result, menus);
        //把菜单写进map,传递给beelt前端s
        map.addAttribute("menus", result);
        map.addAttribute("allMenus", menus);
        map.addAttribute("admin", SessionHolder.isAdmin());
        map.addAttribute("user", SessionHolder.getUser());
        return "main";
    }

    /**
     * 获取验证码
     *
     * @throws IOException
     */
    @RequestMapping(value = "/vcode", method = RequestMethod.GET)
    @ApiOperation("获取验证码")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(value = "手机号码", name = "phone", dataType = "String", paramType = "query"),
                    @ApiImplicitParam(value = "邮箱账号", name = "email", dataType = "String", paramType = "query"),
                    @ApiImplicitParam(value = "是否需要验证账号存在(true验证，false:默认不验证)", name = "type", defaultValue = "false", dataType = "Boolean", paramType = "query")
            }
    )
    public void code(@ApiIgnore final HttpServletResponse response, @ApiIgnore final HttpServletRequest request) throws IOException {
        String type = request.getParameter("type");
        //创建随机验证码
        //String vcode = ValidateCodeUtils.createRandomCode();
        //默认123456 方便调试
        String vcode = "123456";
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        if (ValidateUtils.isNotBlank(phone)) {
            if (!RegUtils.checkCellphone(phone)) {
                throw new BusinessException("手机号格式不正确");
            }
            if (type != null && type.equals("true")) {
                UserVO userVO = new UserVO();
                userVO.setPhone(phone);
                userVO.setValid(true);
                if (userExtService.queryByCondition(userVO).size() > 0) {
                    throw new BusinessException("该手机已存在");
                }
            }
            //往验证码缓存放数据
            cache.putCode(phone, vcode);
            //发短信
            Sms2Utils.sendMsg(phone, vcode);
            ResponseJson json = new ResponseJson();
            json.setData(vcode);
            json.setMsg("操作成功!");
            json.setStatus(200);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(FastJsonUtils.toJSONString(json));
        } else if (ValidateUtils.isNotBlank(email)) {
            if (!RegUtils.checkMail(email)) {
                throw new BusinessException("该邮箱账号不正确");
            }
            if (type != null && type.equals("true")) {
                UserVO userVO = new UserVO();
                userVO.setEmail(email);
                userVO.setValid(true);
                if (userExtService.queryByCondition(userVO).size() > 0) {
                    throw new BusinessException("该邮箱账号已存在");
                }
            }
            //往验证码缓存放数据
            cache.putCode(email, vcode);
            Sms2Utils.sendEmail(email, vcode);
            ResponseJson json = new ResponseJson();
            json.setData(vcode);
            json.setMsg("操作成功!");
            json.setStatus(200);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(FastJsonUtils.toJSONString(json));
        } else {
            response.setContentType("image/jpeg");
            ValidateCodeUtils.output(vcode, response.getOutputStream());
        }
    }

    /**
     * 验证手机号是否存在
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ApiOperation(value = "验证手机号是否存在(返回false：不存在，true：表示存在)", response = Boolean.class)
    @ApiImplicitParams({@ApiImplicitParam(value = "手机号", name = "phone", dataType = "String", paramType = "query", required = true)})
    @ApiResponseDetail(value = Object.class, description = "返回false：不存在，true：表示存在")
    @ResponseBody
    public Object check(@ApiIgnore String phone) {
        //验证手机格式
        if (!RegUtils.checkCellphone(phone)) {
            throw new BusinessException("手机号格式不正确");
        }
        UserDTO userVO = new UserDTO();
        userVO.setPhone(phone);
        userVO.setValid(true);
        //查询是否存在用户
        if (userExtService.queryByCondition(userVO).size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 实现修改密码
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    @ApiOperation(value = "通过手机号或者邮箱修改密码")
    @ApiImplicitParams({@ApiImplicitParam(value = "获取验证码接口返回的验证码", name = "vcode", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(value = "手机号", name = "phone", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(value = "邮箱账号", name = "email", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(value = "新密码(md5加密)", name = "password", dataType = "String", required = true, paramType = "query")})
    @ResponseBody
    public Object updatePwd(@ApiIgnore final UserVO user, @ApiIgnore final HttpSession session) {
        if (ValidateUtils.isBlank(user.getVcode())) {
            throw new BusinessException("验证码不能为空");
        }
        if (CommonUtils.allIsBlank(user.getPhone(), user.getEmail())) {
            throw new BusinessException("手机号与邮箱账号至少一个不为空");
        }
        //比较手机验证码
        if (ValidateUtils.isNotBlank(user.getPhone()) && !CommonUtils.compareString(user.getVcode(), cache.getCode(user.getPhone()))) {
            throw new BusinessException("验证码错误");
        }
        //比较邮件验证码
        if (ValidateUtils.isNotBlank(user.getEmail()) && !CommonUtils.compareString(user.getVcode(), cache.getCode(user.getEmail()))) {
            throw new BusinessException("验证码错误");
        }
        if (CommonUtils.hasBlank(user.getPassword())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        try {
            user.setPassword(user.getPassword().toLowerCase());
            //修改密码
            Object result = userExtService.doUpdatePwd(user);
            cache.removeCode(user.getPhone());
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 实现登录逻辑
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ApiOperation(value = "登录", response = String.class)
    @ApiResponseDetail(value = Object.class, description = "返回Token")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "用户名", name = "username", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(value = "密码(md5加密)", name = "password", dataType = "String", required = true, paramType = "query")})
    @ResponseBody
    public Object doLogin(@ApiIgnore final UserVO user, @ApiIgnore final HttpSession session) {
        //验证是否为空
        if (CommonUtils.hasBlank(user.getUsername(), user.getPassword())) {
            throw new BusinessException(SystemConstant.NULL_MUSTPARAMETER);
        }
        try {
            user.setPassword(user.getPassword().toLowerCase());
            //登录
            LoginUser object = userService.queryUserByCondition(user);
            return TokenUtils.genetatorToken(object.getId());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 默认页（主页）
     *
     * @return
     */
    @RequestMapping("/default")
    public String defaultpage(Map<String, Object> map) {
        Map<String, Object> allInfo = userService.getAll();
        map.putAll(allInfo);
        return "default";
    }
}
