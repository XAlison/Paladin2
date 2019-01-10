package org.zhangxiao.paladin2.core.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.zhangxiao.paladin2.core.admin.AdminConst;
import org.zhangxiao.paladin2.core.admin.entity.SysPermissionResource;
import org.zhangxiao.paladin2.core.admin.mapper.SysPermissionResourceMapper;
import org.zhangxiao.paladin2.core.admin.service.ISysPermissionResourceService;

import java.util.ArrayList;
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

    private List<SysPermissionResource> apiPermissionList = null;

    @Override
    public String[] getApiPermission(String requestURI) {
        List<SysPermissionResource> allPermission = getApiPermissionListCache();
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
    public List<SysPermissionResource> getApiPermissionListCache() {
        if (apiPermissionList == null) {
            apiPermissionList = baseMapper.getListByTypeId(AdminConst.PERMISSION_RESOURCE_TYPE_API);
        }
        return apiPermissionList;
    }

    @Override
    public void cleanApiPermissionListCache() {
        apiPermissionList = null;
    }
}
