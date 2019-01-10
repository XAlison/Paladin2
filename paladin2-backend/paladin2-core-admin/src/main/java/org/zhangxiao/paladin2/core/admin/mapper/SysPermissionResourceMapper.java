package org.zhangxiao.paladin2.core.admin.mapper;

import org.apache.ibatis.annotations.Param;
import org.zhangxiao.paladin2.core.admin.entity.SysPermissionResource;
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
public interface SysPermissionResourceMapper extends BaseMapper<SysPermissionResource> {

    List<SysPermissionResource> getListByTypeId(@Param("typeId") Integer typeId);
}
