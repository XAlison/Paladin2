package org.zhangxiao.paladin2.core.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.zhangxiao.paladin2.core.admin.bean.AdminRowVO;
import org.zhangxiao.paladin2.core.admin.entity.SysAdmin;

import java.util.List;

/**
 * <p>
 * 系统管理员 Mapper 接口
 * </p>
 */
@Component
public interface SysAdminMapper extends BaseMapper<SysAdmin> {

    int countByAccount(@Param("account") String account);

    List<AdminRowVO> getRowVOList();

    AdminRowVO getRowVO(@Param("adminId") Long adminId);
}
