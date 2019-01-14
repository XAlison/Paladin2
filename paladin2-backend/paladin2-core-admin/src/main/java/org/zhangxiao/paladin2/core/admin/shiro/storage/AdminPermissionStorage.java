package org.zhangxiao.paladin2.core.admin.shiro.storage;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

/**
 * 可以根据此接口实现自己的Storage，方便扩展
 * 请在ShiroConfig中配置bean
 */
public interface AdminPermissionStorage {

    SimpleAuthorizationInfo get(Long adminId);

    void put(Long adminId, SimpleAuthorizationInfo authorizationInfo);

    void remove(Long adminId);
}
