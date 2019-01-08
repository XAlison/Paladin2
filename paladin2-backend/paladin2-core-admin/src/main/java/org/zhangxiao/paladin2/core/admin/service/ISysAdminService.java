package org.zhangxiao.paladin2.core.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.bean.AdminDTO;
import org.zhangxiao.paladin2.core.admin.bean.AdminRowVO;
import org.zhangxiao.paladin2.core.admin.entity.SysAdmin;

import java.util.List;

/**
 * <p>
 * 系统管理员 服务类
 * </p>
 *
 * @author 听风zx
 * @since 2019-01-07
 */
public interface ISysAdminService extends IService<SysAdmin> {


    String md5Psw(String password);

    /**
     * 获取管理员列表
     *
     * @return AdminRowVO列表
     */
    List<AdminRowVO> getList();

    /**
     * 保存管理员（adminId==null->创建、adminId!=null->更新）
     */
    void saveOne(Long adminId, AdminDTO adminDTO) throws BizException;

    /**
     * 删除管理员
     */
    void deleteOne(Long adminId) throws BizException;

    /**
     * 获取单个管理员数据
     *
     * @return 单个AdminRowVO，如果不存在，则返回一个AdminRowVO的新实力
     */
    AdminRowVO getOne(Long adminId) throws BizException;
}
