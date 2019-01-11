package org.zhangxiao.paladin2.core.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.shiro.subject.Subject;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.bean.NavNodeVO;
import org.zhangxiao.paladin2.core.admin.bean.PermissionDTO;
import org.zhangxiao.paladin2.core.admin.bean.PermissionVO;
import org.zhangxiao.paladin2.core.admin.entity.SysPermission;

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

    /**
     * 获取某个管理员的已授权的权限表达式列表
     */
    List<String> getAdminPermission(Long adminId);
    /**
     * 获取权限对象树
     */
    List<PermissionVO> getPermissionTree();

    /**
     * 根据某个权限表达式获取下级权限
     */
    List<PermissionVO> getListByParent(String permission);

    /**
     * 获取所有已存在的权限表达式列表
     */
    List<String> getAllPermission();

    /**
     * 清除权限数据缓存 TODO 有变更的时候要清理下
     */
    void cleanPermissionCache();

    /**
     * 根据某个权限表达式，获取最后一个标签
     * a:b:c:d，获取最后一个
     */
    String getLastTag(String permission);

    /**
     * 判断是否持有当前权限或任何子权限
     * 由于前端路径权限判断与shiro有所区别，所以：
     * 先，判断是否有该权限授权
     * 再，遍历子权限，判断是否有子权限授权
     */
    boolean hasAnyPermitted(Subject subject, String permission);

    String getParentPermission(String permission);

    /**
     * 创建一个新权限
     */
    void saveOne(PermissionDTO permissionDTO);

    /**
     * 删除一个权限
     */
    void deleteOne(String permission) throws BizException;
}
