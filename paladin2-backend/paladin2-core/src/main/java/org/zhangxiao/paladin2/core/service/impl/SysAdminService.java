package org.zhangxiao.paladin2.core.service.impl;

import org.zhangxiao.paladin2.core.entity.SysAdmin;
import org.zhangxiao.paladin2.core.mapper.SysAdminMapper;
import org.zhangxiao.paladin2.core.service.ISysAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统管理员 服务实现类
 * </p>
 *
 * @author 听风zx
 * @since 2019-01-07
 */
@Service
public class SysAdminService extends ServiceImpl<SysAdminMapper, SysAdmin> implements ISysAdminService {

}
