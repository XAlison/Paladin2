package org.zhangxiao.paladin2.core.admin.service.impl;

import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.AdminBizError;
import org.zhangxiao.paladin2.core.admin.bean.RolePermissionDTO;
import org.zhangxiao.paladin2.core.admin.entity.SysRolePermission;
import org.zhangxiao.paladin2.core.admin.mapper.SysRolePermissionMapper;
import org.zhangxiao.paladin2.core.admin.service.ISysRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public void savePermissions(Long roleId, List<String> permissionList) throws BizException {
        deletePermissions(roleId);
        if (permissionList != null && permissionList.size() > 0) {
            baseMapper.createPermissions(roleId, permissionList);
        }
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
