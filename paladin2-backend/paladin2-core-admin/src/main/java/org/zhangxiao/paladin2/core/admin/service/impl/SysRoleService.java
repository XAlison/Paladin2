package org.zhangxiao.paladin2.core.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.AdminBizError;
import org.zhangxiao.paladin2.core.admin.bean.RoleDTO;
import org.zhangxiao.paladin2.core.admin.bean.RolePermissionDTO;
import org.zhangxiao.paladin2.core.admin.entity.SysRole;
import org.zhangxiao.paladin2.core.admin.mapper.SysRoleMapper;
import org.zhangxiao.paladin2.core.admin.service.ISysRoleService;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 听风zx
 * @since 2019-01-07
 */
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysAdminRoleService sysAdminRoleService;

    @Override
    public List<SysRole> getList() {
        return baseMapper.getList();
    }

    @Override
    public void createRole(RoleDTO roleDTO) {
        SysRole sysRole = new SysRole();
        sysRole.setTitle(roleDTO.getTitle())
                .setDes(roleDTO.getDes());
        sysRole.insert();
    }

    @Override
    public void updateRole(Long roleId, RoleDTO roleDTO) throws BizException {
        SysRole sysRole = Optional.ofNullable(roleId).map(id->baseMapper.selectById(roleId))
                .orElseThrow(new BizException(AdminBizError.ROLE_NOT_EXIST));
        sysRole.setTitle(roleDTO.getTitle());
        sysRole.setDes(roleDTO.getDes());
        sysRole.updateById();
    }

    @Override
    public void deleteRole(Long roleId) throws BizException {
        if (sysAdminRoleService.countRoleUserNum(roleId) > 0) {
            throw new BizException(AdminBizError.ROLE_IN_USE.suffix(",无法删除"));
        }
        baseMapper.deleteById(roleId);
        // 删除角色所持有的关联权限
        sysRolePermissionService.deletePermissions(roleId);

    }

    @Override
    public void savePermissions(Long roleId, RolePermissionDTO dto) throws BizException {
        SysRole sysRole = Optional.ofNullable(roleId).map(id->baseMapper.selectById(roleId))
                .orElseThrow(new BizException(AdminBizError.ROLE_NOT_EXIST));

        if (dto.hasDuplicatePermission()) {
            throw new BizException(AdminBizError.ROLE_PERMISSION_DUPLICATE);
        }
        sysRolePermissionService.savePermissions(roleId, dto.getPermissionList());

    }

    @Override
    public List<String> getPermissions(Long roleId) throws BizException {
        SysRole sysRole = Optional.ofNullable(roleId).map(id->baseMapper.selectById(roleId))
                .orElseThrow(new BizException(AdminBizError.ROLE_NOT_EXIST));
        return sysRolePermissionService.getPermissions(roleId);
    }
}
