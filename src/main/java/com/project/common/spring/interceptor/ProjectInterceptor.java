package com.project.common.spring.interceptor;

import com.project.common.bean.LockUser;
import com.project.common.bean.LoginUser;
import com.project.common.constant.SystemConstant;
import com.project.common.spring.SessionHolder;
import com.project.common.util.CommonUtils;
import com.project.common.util.ValidateUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 项目的拦截器（进行权限控制）
 *
 * @version 2018年7月24日
 */
public class ProjectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        // 获取到请求路径
        String requestPath = getCompleteRequestPath(request);
        // 直接能访问的url
        if (isPermitedUrl(requestPath)) {
            return true;
        }
        // 计算出服务的项目的请求上下文路径
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

        //验证token是否有效
        if (!SessionHolder.validToken()) {
            // 如果Token无效,做跳转处理
            return toLoginOrNoToken(request, response, basePath);
        }

        //通过token获取登录用户
        LoginUser userVo = SessionHolder.getUser();
        if (CommonUtils.hasBlank(userVo)) {
            // 如果Redis里的user为空,做跳转处理
            return toLoginOrNoToken(request, response, basePath);
        }
        //超级管理员直接通过
        if (SessionHolder.isAdmin()) {
            return true;
        }

        //如果锁定用户id数组包含当前用户的id，就提示用户被锁定
        if (LockUser.userIds.contains(userVo.getId()) && !requestPath.replace("/", "").contains("userexit")) {
            return toLoginOrNoActive(request, response, basePath);
        }
        // 验证当前用户是否对该请求路径有操作权限
        if (!hasMenu(userVo.getPassUrls(), requestPath)) {
            // 是否能访问
            Boolean can = false;
            String path = requestPath.replace("/", "").replace("\\", "");

            //如果请求地址以api开头（app接口），直接通过
            if (path.startsWith("api") && requestPath.contains("api/")) {
                can = true;
            }
            if (!can) {
                //做跳转处理
                return toLoginOrNoAuth(request, response, basePath);
            }
        }
        return true;
    }

    /**
     * 做跳转登录页面或者token失效处理
     *
     * @param request
     * @param response
     * @return
     */
    private boolean toLoginOrNoToken(HttpServletRequest request, HttpServletResponse response, String basePath) throws ServletException, IOException {
        // 如果已manage结尾，就跳转登录页面
        if (request.getRequestURI().replace("//", "/").replace("/", "").toLowerCase().endsWith("manage")) {
            response.sendRedirect(basePath + "login/login");
            return false;
        }
        // 否则就跳转到TOKEN失效接口处理
        request.getRequestDispatcher("/" + request.getContextPath() + "common/notoken").forward(request, response);
        return false;
    }

    /**
     * 做跳转登录页面或者没有权限处理
     *
     * @param request
     * @param response
     * @return
     */
    private boolean toLoginOrNoAuth(HttpServletRequest request, HttpServletResponse response, String basePath) throws ServletException, IOException {
        // 如果已manage结尾，就跳转登录页面
        if (request.getRequestURI().replace("//", "/").replace("/", "").toLowerCase().endsWith("manage")) {
            response.sendRedirect(basePath + "login/login");
            return false;
        }
        // 否则就跳转到TOKEN失效接口处理
        request.getRequestDispatcher("/" + request.getContextPath() + "common/noauth").forward(request, response);
        return false;
    }

    /**
     * 做跳转登录页面或者没有激活处理
     *
     * @param request
     * @param response
     * @return
     */
    private boolean toLoginOrNoActive(HttpServletRequest request, HttpServletResponse response, String basePath) throws ServletException, IOException {
        // 如果已manage结尾，就跳转登录页面
        if (request.getRequestURI().replace("//", "/").replace("/", "").toLowerCase().endsWith("manage")) {
            response.sendRedirect(basePath + "login/login");
            return false;
        }
        // 否则就跳转到TOKEN失效接口处理
        request.getRequestDispatcher("/" + request.getContextPath() + "common/noactive").forward(request, response);
        return false;
    }

    /**
     * 获取完整的请求路径
     *
     * @param request
     * @return
     */
    private String getCompleteRequestPath(HttpServletRequest request) {
        String requestPath = null;
        if (request.getRequestURI().contains(request.getContextPath())) {
            requestPath = request.getRequestURI().substring(0, request.getRequestURI().indexOf(request.getContextPath()))
                    + request.getRequestURI().substring(request.getRequestURI().indexOf(request.getContextPath()) + request.getContextPath().length());
        } else {
            requestPath = request.getRequestURI();
        }
        return requestPath;
    }

    /**
     * 比较用户是否有权限 先等值匹配，在前缀比较url preMatchs
     *
     * @param menus 菜单
     * @param url   地址
     * @return
     */
    private boolean hasMenu(final List<String> menus, final String url) {
        String path = url.replace("/", "").replace("\\", "");
        //前缀匹配
        String preMatchs[] = new String[]{"invalid", "revalid"};
        //后坠匹配 适用于所有管理页面的编辑功能(edit结尾的所有请求都通过)
        String suffixMatchs[] = new String[]{"edit"};
        for (String menu : menus) {
            // 菜单地址不为空
            if (ValidateUtils.isNotBlank(menu)) {
                String menuStr = menu.replace("/", "");
                if (path.equals(menuStr)) {
                    return true;
                }
                for (String preMatch : preMatchs) {
                    if (menuStr.endsWith(preMatch) && path.startsWith(menuStr)) {
                        return true;
                    }
                }
                for (String preMatch : suffixMatchs) {
                    if (path.endsWith(preMatch)) {
                        return true;
                    }
                }
                //已manage结尾,且菜单包含path
                if (path.endsWith("manage") && menuStr.startsWith(path)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {

    }

    /**
     * 判断是否为允许直接访问的url
     *
     * @param url
     * @return
     */
    private boolean isPermitedUrl(final String url) {
        String path = url.replace("\\", "").replace("/", "");
        String[] permitedUrls = SystemConstant.PERMITED_URLS;
        for (String permitedUrl : permitedUrls) {
            // 例如tool/**
            if (permitedUrl.endsWith("/**")) {
                // 只要path包含tool，就可以通过
                if (path.contains(permitedUrl.replace("/**", "").replace("/", ""))) {
                    return true;
                }
            }
            if (path.equals((permitedUrl.replace("/", "")))) {
                return true;
            }
        }
        return false;
    }

    private class Invalid {

    }
}
