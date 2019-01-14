package org.zhangxiao.paladin2.core.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zhangxiao.paladin2.core.admin.entity.SysAdminRole;
import org.zhangxiao.paladin2.core.admin.mapper.SysAdminRoleMapper;
import org.zhangxiao.paladin2.core.admin.service.ISysAdminRoleService;
import org.zhangxiao.paladin2.core.admin.shiro.AdminPermissionStorage;

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
public class SysAdminRoleService extends ServiceImpl<SysAdminRoleMapper, SysAdminRole> implements ISysAdminRoleService {

    @Autowired
    private AdminPermissionStorage adminPermissionStorage;

    @Override
    public List<Long> getRoleIdList(Long adminId) {
        return baseMapper.getRoleIdList(adminId);
    }

    @Override
    public void saveRelation(Long adminId, List<Long> roleIdList) {
        deleteRelation(adminId);
        if (roleIdList != null && roleIdList.size() > 0) {
            baseMapper.saveRelation(adminId, roleIdList);
        }
        adminPermissionStorage.remove(adminId);
    }

    @Override
    public void deleteRelation(Long adminId) {
        baseMapper.deleteRelation(adminId);
    }

    @Override
    public int countRoleUserNum(Long roleId) {
        return baseMapper.countRoleUseNum(roleId);
    }

    @Override
    public List<Long> getAdminIdList(Long roleId) {
        return baseMapper.getAdminIdList(roleId);
    }
}
