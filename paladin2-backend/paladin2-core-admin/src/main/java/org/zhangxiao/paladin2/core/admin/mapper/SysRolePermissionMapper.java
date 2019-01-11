package org.zhangxiao.paladin2.core.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.zhangxiao.paladin2.core.admin.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 */
@Component
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    void deleteByRoleId(@Param("roleId") Long roleId);

    void createPermissions(@Param("roleId") Long roleId, @Param("permissionList") List<String> permissionList);

    List<String> getList(@Param("roleId") Long roleId);
}
