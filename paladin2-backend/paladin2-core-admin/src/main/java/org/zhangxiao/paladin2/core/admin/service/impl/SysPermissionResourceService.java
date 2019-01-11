package org.zhangxiao.paladin2.core.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.AdminBizError;
import org.zhangxiao.paladin2.core.admin.AdminConst;
import org.zhangxiao.paladin2.core.admin.bean.NavNodeVO;
import org.zhangxiao.paladin2.core.admin.bean.PermissionResourceDTO;
import org.zhangxiao.paladin2.core.admin.bean.PermissionVO;
import org.zhangxiao.paladin2.core.admin.bean.UiPermissionVO;
import org.zhangxiao.paladin2.core.admin.entity.SysPermissionResource;
import org.zhangxiao.paladin2.core.admin.mapper.SysPermissionResourceMapper;
import org.zhangxiao.paladin2.core.admin.service.ISysPermissionResourceService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
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
    private Hashtable<Integer, List<SysPermissionResource>> resourcesCache = new Hashtable<>();

    @Override
    public String[] getApiPermission(String requestURI) {
        List<SysPermissionResource> allApiResources = getResources(AdminConst.PERMISSION_RESOURCE_TYPE_API);
        List<String> goodsPermission = new ArrayList<>();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (SysPermissionResource resource : allApiResources) {
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
    public List<SysPermissionResource> getResources(Integer typeId) {
        List<SysPermissionResource> listInCache = resourcesCache.getOrDefault(typeId, null);
        if (listInCache == null) {
            listInCache = baseMapper.getListByTypeId(typeId);
            resourcesCache.put(typeId, listInCache);
        }
        return listInCache;
    }

    @Override
    public void cleanResourcesCache() {
        resourcesCache = new Hashtable<>();
    }

    @Override
    public void cleanResourcesCache(Integer typeId) {
        resourcesCache.remove(typeId);
    }

    @Override
    public UiPermissionVO getPermittedUIPermission(Subject subject) {
        UiPermissionVO vo = new UiPermissionVO();
        vo.setUiPaths(getUiPermission(subject, AdminConst.PERMISSION_RESOURCE_TYPE_UI_PATH));
        // vo.setUiElements(getUiPermission(subject, AdminConst.PERMISSION_RESOURCE_TYPE_UI_ELEMENT)); TODO 前端UI元素权限
        vo.setUiElements(new HashSet<>());
        vo.setUiNavs(getPermittedNavs(subject));
        return vo;
    }

    @Override
    public List<SysPermissionResource> getResourcesByPermission(String permission) {
        return baseMapper.getListByPermission(permission);
    }

    @Override
    public void createOne(PermissionResourceDTO dto) throws BizException {
        SysPermissionResource resource = baseMapper.getOne(dto.getPermission(), dto.getTypeId(), dto.getData());
        if (resource != null) {
            throw new BizException(AdminBizError.PERMISSION_RESOURCE_EXIST);
        }
        resource = new SysPermissionResource();
        resource.setPermission(dto.getPermission());
        resource.setTypeId(dto.getTypeId());
        resource.setData(dto.getData());
        if (resource.insert()) {
            this.cleanResourcesCache();
        }
    }

    @Override
    public void deleteOne(PermissionResourceDTO dto) {
        SysPermissionResource resource = baseMapper.getOne(dto.getPermission(), dto.getTypeId(), dto.getData());
        if (resource != null && resource.deleteById()) {
            this.cleanResourcesCache(dto.getTypeId());
        }
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
    private HashSet<String> getUiPermission(Subject subject, int permissionResourceTypeUiPath) {
        // 获取所有前端路径级对应的权限
        List<SysPermissionResource> uiPathResources = getResources(permissionResourceTypeUiPath);
        HashSet<String> uiPaths = new HashSet<>();
        uiPathResources.forEach(item -> {
            // 如果这个路径，已经在允许访问的路径集合中，那就跳过
            if (uiPaths.contains(item.getData())) {
                return;
            }
            // 如果是超级管理员，或者 验证有权限，就添加到允许访问的路径集合中
            if ("1".equals(subject.getPrincipal().toString()) || sysPermissionService.hasAnyPermitted(subject, item.getPermission())) {
                uiPaths.add(item.getData());
            }
        });
        return uiPaths;
    }

    private List<NavNodeVO> getPermittedNavs(Subject subject) {
        List<PermissionVO> fullNavs = sysPermissionService.getPermissionTree();
        List<NavNodeVO> finalNavs = new ArrayList<>();
        for (PermissionVO level1 : fullNavs) {
            if ("1".equals(subject.getPrincipal().toString()) || sysPermissionService.hasAnyPermitted(subject, level1.getPermission())) {
                NavNodeVO level1Node = convertToNavNodeVO(level1);
                for (PermissionVO level2 : level1.getChildren()) {
                    if ("1".equals(subject.getPrincipal().toString()) || sysPermissionService.hasAnyPermitted(subject, level2.getPermission())) {
                        NavNodeVO level2Node = convertToNavNodeVO(level2);
                        level1Node.getChildren().add(level2Node);
                    }
                }
                finalNavs.add(level1Node);
            }
        }
        return finalNavs;
    }

    private NavNodeVO convertToNavNodeVO(PermissionVO permissionVO) {
        NavNodeVO nodeVO = new NavNodeVO();
        String tag = sysPermissionService.getLastTag(permissionVO.getPermission());
        nodeVO.setTag(tag)
              .setPath(permissionVO.getNavPath())
              .setTitle(permissionVO.getTitle());
        return nodeVO;
    }

}
