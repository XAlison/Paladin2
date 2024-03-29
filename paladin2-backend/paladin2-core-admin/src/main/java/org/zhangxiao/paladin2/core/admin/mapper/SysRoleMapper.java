package org.zhangxiao.paladin2.core.admin.mapper;

import org.zhangxiao.paladin2.core.admin.entity.SysRole;
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
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> getList();
}
