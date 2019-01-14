package org.zhangxiao.paladin2.core.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.entity.SysRolePermission;
import org.zhangxiao.paladin2.core.admin.mapper.SysRolePermissionMapper;
import org.zhangxiao.paladin2.core.admin.service.ISysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.zhangxiao.paladin2.core.admin.shiro.storage.AdminPermissionStorage;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 听风zx
 * @since 2019-01-07
 */
@Service
public class SysRolePermissionService extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Autowired
    private AdminPermissionStorage adminPermissionStorage;
    @Autowired
    private SysAdminRoleService sysAdminRoleService;

    @Override
    public void savePermissions(Long roleId, List<String> permissionList) throws BizException {
        deletePermissions(roleId);
        if (permissionList != null && permissionList.size() > 0) {
            baseMapper.createPermissions(roleId, permissionList);
        }
        sysAdminRoleService.getAdminIdList(roleId)
                .forEach(adminId-> adminPermissionStorage.remove(adminId));
    }

    @Override
    public void deletePermissions(Long roleId) {
        baseMapper.deleteByRoleId(roleId);
    }

    @Override
    public List<String> getPermissions(Long roleId) {
        return baseMapper.getList(roleId);
    }
}
