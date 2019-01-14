package org.zhangxiao.paladin2.core.admin.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zhangxiao.paladin2.core.admin.AdminConst;
import org.zhangxiao.paladin2.core.admin.entity.SysAdmin;
import org.zhangxiao.paladin2.core.admin.service.impl.SysAdminService;
import org.zhangxiao.paladin2.core.admin.service.impl.SysPermissionService;
import org.zhangxiao.paladin2.core.admin.shiro.storage.AdminPermissionStorage;

public class AdminRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(AdminRealm.class);
    @Autowired
    private SysAdminService sysAdminService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private AdminPermissionStorage adminPermissionStorage;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof AdminToken;
    }


    /**
     * 获取用户验证信息
     *
     * @param token 所需验证的token
     * @return null or 身份信息
     * @throws AuthenticationException 验证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        AdminToken adminToken = (AdminToken) token;
        if (log.isDebugEnabled()) {
            log.debug(String.format("doGetAuthenticationInfo拿到token\nadminToken:%s\nprincipal:%s\ncredentials:%s"
                    , adminToken.toString()
                    , adminToken.getPrincipal().toString()
                    , adminToken.getCredentials().toString()));
        }
        SysAdmin admin = sysAdminService.getById(Long.parseLong(adminToken.getPrincipal().toString()));
        if (admin != null) {
            if (admin.getStatus() == AdminConst.ADMIN_STATUS_DISABLED) {
                throw new AuthenticationException("用户已被禁用");
            }
            return new SimpleAuthenticationInfo(adminToken.getPrincipal(), adminToken.getCredentials(), admin.getNickName());
        } else {
            throw new AuthenticationException("用户不存在");
        }

    }

    /**
     * 获取用户授权信息
     *
     * @param principals 用户身份
     * @return null or 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("AuthorizationInfo");
        log.debug(principals.getPrimaryPrincipal().toString());
        Long adminId = Long.valueOf(principals.getPrimaryPrincipal().toString());
        // 获取当前管理员所有角色所有权限
        SimpleAuthorizationInfo authorizationInfo = adminPermissionStorage.get(adminId);
        if (authorizationInfo == null) {
            authorizationInfo = new SimpleAuthorizationInfo();
            authorizationInfo.addStringPermissions(sysPermissionService.getAdminPermission(adminId));
            adminPermissionStorage.put(adminId, authorizationInfo);
        }
        return authorizationInfo;
    }

}
