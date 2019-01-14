package org.zhangxiao.paladin2.core.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.bean.RolePermissionDTO;
import org.zhangxiao.paladin2.core.admin.entity.SysRolePermission;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 听风zx
 * @since 2019-01-07
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 保存某个角色的权限
     */
    void savePermissions(Long roleId, List<String> permissionList) throws BizException;
    /**
     * 删除某个角色的权限（删除角色的时候使用）
     */
    void deletePermissions(Long roleId);
    /**
     * 获取某个角色的权限
     */
    List<String> getPermissions(Long roleId);
}
