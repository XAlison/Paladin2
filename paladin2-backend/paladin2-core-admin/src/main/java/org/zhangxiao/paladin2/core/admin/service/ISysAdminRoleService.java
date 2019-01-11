package org.zhangxiao.paladin2.core.admin.service;

import org.zhangxiao.paladin2.core.admin.entity.SysAdminRole;
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
public interface ISysAdminRoleService extends IService<SysAdminRole> {

    /**
     * 获取某个管理员的角色ID列表
     * @param adminId 管理员ID
     * @return 角色ID列表
     */
    List<Long> getRoleIdList(Long adminId);

    /**
     * 保存管理员与角色关系
     * @param adminId 管理员ID
     * @param roleIdList 角色ID列表
     */
    void saveRelation(Long adminId, List<Long> roleIdList);

    /**
     * 删除管理员与角色的关联
     * @param adminId 管理员ID
     */
    void deleteRelation(Long adminId);

    int countRoleUserNum(Long roleId);
}
