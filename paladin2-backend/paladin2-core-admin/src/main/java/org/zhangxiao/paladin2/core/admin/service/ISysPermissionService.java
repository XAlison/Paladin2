package org.zhangxiao.paladin2.core.admin.service;

import org.zhangxiao.paladin2.core.admin.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统-权限表 服务类
 * </p>
 *
 * @author 听风zx
 * @since 2019-01-07
 */
public interface ISysPermissionService extends IService<SysPermission> {

    List<String> getAdminPermission(Long adminId);
}
