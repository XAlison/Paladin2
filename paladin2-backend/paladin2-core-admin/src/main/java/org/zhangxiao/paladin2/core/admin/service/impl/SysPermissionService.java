package org.zhangxiao.paladin2.core.admin.service.impl;

import org.zhangxiao.paladin2.core.admin.entity.SysPermission;
import org.zhangxiao.paladin2.core.admin.mapper.SysPermissionMapper;
import org.zhangxiao.paladin2.core.admin.service.ISysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统-权限表 服务实现类
 * </p>
 *
 * @author 听风zx
 * @since 2019-01-07
 */
@Service
public class SysPermissionService extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Override
    public List<String> getAdminPermission(Long adminId) {
        return baseMapper.getAdminPermission(adminId);
    }
}
