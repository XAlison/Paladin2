package org.zhangxiao.paladin2.core.admin.mapper;

import org.zhangxiao.paladin2.core.admin.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 系统-权限表 Mapper 接口
 * </p>
 *
 */
@Component
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<String> getAdminPermission(Long adminId);
}
