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

    void savePermissions(Long roleId, List<String> permissionList) throws BizException;

    void deletePermissions(Long roleId);

    List<String> getPermissions(Long roleId);
}
