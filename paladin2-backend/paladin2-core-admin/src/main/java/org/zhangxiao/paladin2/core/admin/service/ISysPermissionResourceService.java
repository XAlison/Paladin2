package org.zhangxiao.paladin2.core.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.shiro.subject.Subject;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.bean.ApiVO;
import org.zhangxiao.paladin2.core.admin.bean.PermissionResourceDTO;
import org.zhangxiao.paladin2.core.admin.bean.UiPermissionVO;
import org.zhangxiao.paladin2.core.admin.entity.SysPermissionResource;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 听风zx
 * @since 2019-01-07
 */
public interface ISysPermissionResourceService extends IService<SysPermissionResource> {

    /**
     * 获取某个接口所对应的所有权限（有可能这个接口被分配到多个权限中）
     *
     * @param requestURI 接口URI
     * @return shiro权限表达式字符串数组
     */
    String[] getApiPermission(String requestURI);

    /**
     * 获取获取某个类型的资源列表
     * TODO 缓存需要能支持扩展，如Redis
     *
     * @return 权限资源列表
     */
    List<SysPermissionResource> getResources(Integer typeId);

    /**
     * 清除所有的权限资源缓存
     */
    void cleanResourcesCache();

    /**
     * 清除某个类型的权限资源缓存
     */
    void cleanResourcesCache(Integer typeId);

    /**
     * 根据shiro的subject，来获取对应账号的前端权限VO
     */
    UiPermissionVO getPermittedUIPermission(Subject subject);

    /**
     * 获取某个权限的资源
     */
    List<SysPermissionResource> getResourcesByPermission(String permission);

    /**
     * 创建一个权限资源
     */
    void createOne(PermissionResourceDTO dto) throws BizException;

    /**
     * 删除一个权限资源
     */
    void deleteOne(PermissionResourceDTO dto);

    /**
     * 获取/manage下所有路径
     */
    List<ApiVO> getApiUrls();
}
