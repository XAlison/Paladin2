package org.zhangxiao.paladin2.core.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.zhangxiao.paladin2.core.admin.AdminConst;
import org.zhangxiao.paladin2.core.admin.bean.UiPermissionVO;
import org.zhangxiao.paladin2.core.admin.entity.SysPermissionResource;
import org.zhangxiao.paladin2.core.admin.mapper.SysPermissionResourceMapper;
import org.zhangxiao.paladin2.core.admin.service.ISysPermissionResourceService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 听风zx
 * @since 2019-01-07
 */
@Service
public class SysPermissionResourceService extends ServiceImpl<SysPermissionResourceMapper, SysPermissionResource> implements ISysPermissionResourceService {

    private static final Logger log = LoggerFactory.getLogger(SysPermissionResourceService.class);
    @Autowired
    private SysPermissionService sysPermissionService;
    private List<SysPermissionResource> apiResourcesCache = null;

    @Override
    public String[] getApiPermission(String requestURI) {
        List<SysPermissionResource> allPermission = getApiResourcesCache();
        List<String> goodsPermission = new ArrayList<>();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (SysPermissionResource resource : allPermission) {
            if (pathMatcher.match(resource.getData(), requestURI)) {
                if (log.isDebugEnabled()) {
                    log.debug(resource.getData() + "<----匹配---->" + requestURI);
                }
                goodsPermission.add(resource.getPermission());
            }
        }
        return goodsPermission.toArray(new String[]{});
    }

    @Override
    public List<SysPermissionResource> getApiResourcesCache() {
        if (apiResourcesCache == null) {
            apiResourcesCache = baseMapper.getListByTypeId(AdminConst.PERMISSION_RESOURCE_TYPE_API);
        }
        return apiResourcesCache;
    }

    @Override
    public void cleanApiResourcesCache() {
        apiResourcesCache = null;
    }

    @Override
    public UiPermissionVO getPermittedUIPermission() {
        UiPermissionVO vo = new UiPermissionVO();
        vo.setUiPaths(getUiPermission(AdminConst.PERMISSION_RESOURCE_TYPE_UI_PATH));
        vo.setUiElements(getUiPermission(AdminConst.PERMISSION_RESOURCE_TYPE_UI_ELEMENT));
        // vo.setUiNavs(sysPermissionService.getPermittedNavs());
        return vo;
    }

    /**
     * 假设拿到一个资源对象
     * {
     * permission:'config:permission',<---这个路径所对应的权限表达式
     * data:'/config/permission',<---需要判断这个路径是否可以被放行
     * }
     * 该用户实际拥有的permission组中的权限如下：
     * config:permission            无
     * config:permission:admin      无
     * config:permission:role       有
     * config:permission:resource   有
     * 如果直接判断，是没有该路径权限的
     * 但是实际上的需求是，只要有任何子权限，即可放行
     */
    private HashSet<String> getUiPermission(int permissionResourceTypeUiPath) {
        Subject subject = SecurityUtils.getSubject();
        // 获取所有前端路径级对应的权限
        List<SysPermissionResource> uiPathResources = baseMapper.getListByTypeId(permissionResourceTypeUiPath);
        List<String> allPermission = sysPermissionService.getAllPermission();
        HashSet<String> uiPaths = new HashSet<>();
        uiPathResources.forEach(item -> {
            // 如果这个路径，已经在允许访问的路径集合中，那就跳过
            if (uiPaths.contains(item.getData())) {
                return;
            }
            // 如果是超级管理员，或者 验证有权限，就添加到允许访问的路径集合中
            if ("1".equals(subject.getPrincipal().toString()) || checkPermission(subject, allPermission, item.getPermission())) {
                uiPaths.add(item.getData());
            }
        });
        return uiPaths;
    }

    /**
     * 由于前端路径权限判断与shiro有所区别，所以：
     * 先，判断是否有该权限授权
     * 再，遍历子权限，判断是否有子权限授权
     */
    private boolean checkPermission(Subject subject, List<String> allPermission, String permission) {
        for (String item : allPermission) {
            if (item.startsWith(permission) && subject.isPermitted(item)) {
                return true;
            }
        }
        return false;
    }

}
