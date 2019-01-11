package org.zhangxiao.paladin2.core.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.zhangxiao.paladin2.core.admin.entity.SysAdminRole;
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
public interface SysAdminRoleMapper extends BaseMapper<SysAdminRole> {

    List<Long> getList(@Param("adminId") Long adminId);

    void deleteRelation(@Param("adminId") Long adminId);

    void saveRelation(@Param("adminId") Long adminId, @Param("roleIdList") List<Long> roleIdList);

    int countRoleUseNum(@Param("roleId") Long roleId);
}
