package org.zhangxiao.paladin2.core.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.common.util.StrUtils;
import org.zhangxiao.paladin2.core.admin.AdminBizError;
import org.zhangxiao.paladin2.core.admin.bean.AdminDTO;
import org.zhangxiao.paladin2.core.admin.bean.AdminRowVO;
import org.zhangxiao.paladin2.core.admin.bean.ChangePasswordDTO;
import org.zhangxiao.paladin2.core.admin.entity.SysAdmin;
import org.zhangxiao.paladin2.core.admin.mapper.SysAdminMapper;
import org.zhangxiao.paladin2.core.admin.service.ISysAdminService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // 便于在配置中进行更改
    @Value("${paladin2.admin-salt:salt_2019-1-8 09:42:40_paladin2}")
    private String PSW_SALT;

    @Autowired
    private SysAdminRoleService sysAdminRoleService;

    public static void main(String[] args) {
        SysAdminService sysAdminService = new SysAdminService();
        System.out.println(sysAdminService.md5Psw("123"));
    }

    @Override
    public String md5Psw(String password) {
        return StrUtils.md5(password + PSW_SALT);
    }

    @Override
    public List<AdminRowVO> getList() {
        List<AdminRowVO> list = Optional.ofNullable(baseMapper.getRowVOList())
                .orElse(new ArrayList<>());
        list.forEach(item -> item.setRoleIdList(sysAdminRoleService.getRoleIdList(item.getId())));
        return list;
    }

    @Override
    public void createOne(AdminDTO adminDTO) throws BizException {
        SysAdmin sysAdmin = new SysAdmin();
        if (baseMapper.countByAccount(adminDTO.getAccount()) > 0) {
            throw new BizException(AdminBizError.ADMIN_USERNAME_EXIST);
        }
        if (StrUtils.isEmpty(adminDTO.getPassword())) {
            throw new BizException(AdminBizError.ADMIN_PASSWORD_REQUIRED);
        }
        sysAdmin.setAccount(adminDTO.getAccount())
                .setPassword(md5Psw(adminDTO.getPassword()))
                .setCreateTime(LocalDateTime.now())
                .setNickName(adminDTO.getNickName());
        sysAdmin.insert();
        sysAdminRoleService.saveRelation(sysAdmin.getId(), adminDTO.getRoleIdList());
    }

    @Override
    public void updateOne(Long adminId, AdminDTO adminDTO) throws BizException {
        SysAdmin sysAdmin = Optional.ofNullable(adminId)
                .map(id -> baseMapper.selectById(adminId))
                .orElseThrow(new BizException(AdminBizError.ADMIN_NOT_EXIST));
        if (!StrUtils.isEmpty(adminDTO.getPassword())) {
            sysAdmin.setPassword(md5Psw(adminDTO.getPassword()));
        }
        sysAdmin.setNickName(adminDTO.getNickName())
                .setNickName(adminDTO.getNickName())
                .setUpdateTime(LocalDateTime.now());
        sysAdmin.updateById();
        sysAdminRoleService.saveRelation(sysAdmin.getId(), adminDTO.getRoleIdList());
    }

    @Override
    public void deleteOne(Long adminId) throws BizException {
        SysAdmin admin = Optional.ofNullable(baseMapper.selectById(adminId))
                .orElseThrow(new BizException(AdminBizError.ADMIN_NOT_EXIST));
        if (admin.getId().equals(1L)) {
            throw new BizException(AdminBizError.ADMIN_CANNOT_DELETE);
        }
        baseMapper.deleteById(adminId);
        sysAdminRoleService.deleteRelation(adminId);
    }

    @Override
    public AdminRowVO getOne(Long adminId) throws BizException {
        AdminRowVO vo = Optional.ofNullable(baseMapper.getRowVO(adminId))
                .orElseThrow(() -> new BizException(AdminBizError.ADMIN_NOT_EXIST));
        vo.setRoleIdList(sysAdminRoleService.getRoleIdList(adminId));
        return vo;
    }

    @Override
    public void changePassword(Long adminId, ChangePasswordDTO changePasswordDTO) throws BizException {
        SysAdmin admin = Optional.ofNullable(baseMapper.selectById(adminId))
                .orElseThrow(new BizException(AdminBizError.ADMIN_NOT_EXIST));
        if (!md5Psw(changePasswordDTO.getOldPsw()).equals(admin.getPassword())) {
            throw new BizException(AdminBizError.OLD_PSW_ERROR);
        }
        baseMapper.updatePassword(adminId, md5Psw(changePasswordDTO.getNewPsw()));
    }
}
