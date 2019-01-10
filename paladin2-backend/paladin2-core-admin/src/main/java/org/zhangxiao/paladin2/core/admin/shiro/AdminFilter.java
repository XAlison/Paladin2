package org.zhangxiao.paladin2.core.admin.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.zhangxiao.paladin2.common.util.CookieUtils;
import org.zhangxiao.paladin2.common.util.JacksonUtils;
import org.zhangxiao.paladin2.common.util.StrUtils;
import org.zhangxiao.paladin2.core.admin.AdminProperties;
import org.zhangxiao.paladin2.core.admin.service.impl.SysPermissionResourceService;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AdminFilter extends AccessControlFilter {
    private static final Logger log = LoggerFactory.getLogger(AdminFilter.class);
    private static final String UNAUTHORIZED_MESSAGE = "未登录，无权访问";
    private static final String FORBIDDEN_MESSAGE = "未授权，无权访问";
    private SysPermissionResourceService sysPermissionResourceService;
    private AdminProperties adminProperties;

    /**
     * 从请求中获取jwt
     * 优先获取header中jwt，否则获取cookie中的
     */
    public static String getJwtStr(ServletRequest servletRequest) {
        HttpServletRequest request = WebUtils.toHttp(servletRequest);
        String adminJwtStr = request.getHeader("Authorization");
        if (StrUtils.isEmpty(adminJwtStr)) {
            adminJwtStr = CookieUtils.getCookie(request, "Authorization");
        }
        return StringUtils.isEmpty(adminJwtStr) ? "" : adminJwtStr;
    }

    public void setSysPermissionResourceService(SysPermissionResourceService sysPermissionResourceService, AdminProperties adminProperties) {
        this.sysPermissionResourceService = sysPermissionResourceService;
        this.adminProperties = adminProperties;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("isAccessAllowed");
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        String requestURI = httpServletRequest.getRequestURI();
        if (log.isDebugEnabled()) {
            log.debug("onAccessDenied：" + requestURI);
        }
        String jwtStr = getJwtStr(httpServletRequest);
        if (StringUtils.isEmpty(jwtStr)) {
            onLoginFailure(response, UNAUTHORIZED_MESSAGE);
            return false;
        }
        AdminToken adminToken = AdminToken.parse(jwtStr, adminProperties.getJwtSecret());
        if (!adminToken.isAuthenticated()) {
            onLoginFailure(response, UNAUTHORIZED_MESSAGE);
            return false;
        }
        SecurityUtils.getSubject().login(adminToken);
        Subject currentUser = getSubject(request, response);
        // 超级管理员默认不需要授权验证
        if ("1".equals(adminToken.getPrincipal().toString())) {
            return true;
        }
        boolean isAuthorization = false;
        if (!StringUtils.isEmpty(requestURI)) {
            if (log.isDebugEnabled()) {
                log.debug("当前访问URI:" + requestURI);
            }
            if (requestURI.equals("/manage/sys/admin/ui_permission")) {
                // 所有后台用户对以上路径下的接口，均放行
                isAuthorization = true;
            } else {
                // 可能当前URI多个权限使用
                String[] permissionArr = sysPermissionResourceService.getApiPermission(requestURI);
                if (log.isDebugEnabled()) {
                    log.debug(JacksonUtils.toJson(permissionArr));
                }
                boolean[] impliedArr = currentUser.isPermitted(permissionArr);
                for (boolean implied : impliedArr) {
                    if (implied) {
                        isAuthorization = true;
                        break;
                    }
                }
            }
        }
        if (!isAuthorization) {
            onAuthFailure(response);
        }
        return isAuthorization;
    }

    /**
     * 处理异常，避免传递给下个过滤器，产生系统异常
     */
    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing) throws ServletException, IOException {
        log.debug("cleanup");
        if (existing instanceof AuthenticationException) {
            onLoginFailure(response, existing.getMessage());
            existing = null;
        }
        super.cleanup(request, response, existing);
    }

    //region 定义请求响应内容
    // 因为还没有到controller层，所以无法被BizExceptionHandler捕获处理
    private void onAuthFailure(ServletResponse response) {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        try {
            responseFailureJson(servletResponse, HttpStatus.FORBIDDEN.value(), FORBIDDEN_MESSAGE);
        } catch (IOException e) {
        }
    }

    private void onLoginFailure(ServletResponse response, String msg) {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        try {
            responseFailureJson(servletResponse, HttpStatus.UNAUTHORIZED.value(), msg);
        } catch (IOException e) {
        }
    }

    private void responseFailureJson(HttpServletResponse response, int code, String msg) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("status", code);
        msgMap.put("message", msg);
        String responseText = Optional.ofNullable(JacksonUtils.toJson(msgMap)).orElse("");
        response.getWriter().write(responseText);
    }
    //endregion
}
