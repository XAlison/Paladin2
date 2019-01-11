package org.zhangxiao.paladin2.core.admin.service;

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

    List<SysRole> getList();
}
