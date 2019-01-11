package org.zhangxiao.paladin2.core.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.AdminBizError;
import org.zhangxiao.paladin2.core.admin.bean.PermissionDTO;
import org.zhangxiao.paladin2.core.admin.bean.PermissionVO;
import org.zhangxiao.paladin2.core.admin.entity.SysPermission;
import org.zhangxiao.paladin2.core.admin.mapper.SysPermissionMapper;
import org.zhangxiao.paladin2.core.admin.service.ISysPermissionService;

import java.util.List;
import java.util.Optional;

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

    private List<String> allPermissionsCache = null;
    private List<PermissionVO> permissionVOTreeCache = null;

    @Override
    public List<String> getAdminPermission(Long adminId) {
        return baseMapper.getAdminPermission(adminId);
    }

    @Override
    public List<PermissionVO> getListByParent(String permission) {
        return baseMapper.getVOListByParent(permission);
    }

    @Override
    public List<String> getAllPermission() {
        if (allPermissionsCache == null) {
            allPermissionsCache = baseMapper.getAllPermission();
        }
        return allPermissionsCache;
    }

    @Override
    public void cleanPermissionCache() {
        allPermissionsCache = null;
        permissionVOTreeCache = null;
    }

    @Override
    public String getLastTag(String permission) {
        int lastColon = Optional.ofNullable(permission).orElse("").lastIndexOf(":");
        if (lastColon == -1) {
            return permission;
        } else {
            return permission.substring(lastColon + 1);
        }
    }

    @Override
    public List<PermissionVO> getPermissionTree() {
        if (permissionVOTreeCache == null) {
            List<PermissionVO> level1List = baseMapper.getVOListByParent("");
            for (PermissionVO level1Item : level1List) {
                String level1Permission = level1Item.getPermission();
                List<PermissionVO> level2List = baseMapper.getVOListByParent(level1Permission);
                for (PermissionVO level2Item : level2List) {
                    String level2Permission = level2Item.getPermission();
                    List<PermissionVO> level3List = baseMapper.getVOListByParent(level2Permission);
                    level2Item.setChildren(level3List);
                }
                level1Item.setChildren(level2List);
            }
            permissionVOTreeCache = level1List;
        }
        return permissionVOTreeCache;
    }

    @Override
    public boolean hasAnyPermitted(Subject subject, String permission) {
        List<String> allPermission = getAllPermission();
        for (String item : allPermission) {
            if (item.startsWith(permission) && subject.isPermitted(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getParentPermission(String permission) {
        int lastColon = permission.lastIndexOf(":");
        if (lastColon == -1) {
            return "";
        } else {
            return permission.substring(0, lastColon);
        }
    }

    @Override
    public void saveOne(PermissionDTO dto) {
        String parent = getParentPermission(dto.getPermission());
        SysPermission sysPermission = baseMapper.selectById(dto.getPermission());
        if (sysPermission == null) {
            sysPermission = new SysPermission();
        }
        sysPermission.setTitle(dto.getTitle())
            .setPermission(dto.getPermission())
            .setNavPath(dto.getPath())
            .setParent(parent)
            .setSort(dto.getSort())
            .insertOrUpdate();
        // TODO 需要处理权限表达式更新不同步造成的权限不同步问题
        this.cleanPermissionCache();
    }

    @Override
    public void deleteOne(String permission) throws BizException {
        //判断当前权限是否存在
        SysPermission sysPermission = baseMapper.selectById(permission);
        if (sysPermission == null) {
            throw new BizException(AdminBizError.PERMISSION_NOT_EXIST);
        }
        //判断是否有子权限
        if (baseMapper.countChildren(permission) > 0) {
            throw new BizException(AdminBizError.PERMISSION_HAS_CHILDREN);
        }
        sysPermission.deleteById();
        this.cleanPermissionCache();
    }
}
