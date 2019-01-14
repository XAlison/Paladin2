package org.zhangxiao.paladin2.core.admin.service;

import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.bean.RoleDTO;
import org.zhangxiao.paladin2.core.admin.bean.RolePermissionDTO;
import org.zhangxiao.paladin2.core.admin.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 听风zx
 * @since 2019-01-07
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 获取系统所有角色列表
     */
    List<SysRole> getList();

    /**
     * 创建角色
     */
    void createRole(RoleDTO roleDTO);

    /**
     * 更新角色
     */
    void updateRole(Long roleId, RoleDTO roleDTO) throws BizException;

    /**
     * 删除角色
     */
    void deleteRole(Long roleId) throws BizException;

    /**
     * 保存角色权限
     */
    void savePermissions(Long roleId, RolePermissionDTO dto) throws BizException;

    /**
     * 获取角色的权限
     */
    List<String> getPermissions(Long roleId) throws BizException;
}
