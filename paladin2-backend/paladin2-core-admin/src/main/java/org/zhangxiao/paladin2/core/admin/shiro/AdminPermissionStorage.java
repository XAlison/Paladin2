package org.zhangxiao.paladin2.core.admin.shiro;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.springframework.stereotype.Component;

import java.util.Hashtable;

/**
 * 管理员权限内存缓存
 * 在AdminRealm中获取用户授权数据的时候使用，如果存在，则不重新获取
 * TODO 退出时，需要清空
 * TODO 考虑可以扩展Redis作为缓存
 */
@Component
public class AdminPermissionStorage {

    private Hashtable<Long, SimpleAuthorizationInfo> storage = new Hashtable<>();

    public SimpleAuthorizationInfo get(Long adminId) {
        return storage.getOrDefault(adminId, null);
    }

    public void put(Long adminId, SimpleAuthorizationInfo authorizationInfo) {
        storage.put(adminId, authorizationInfo);
    }

    public void remove(Long adminId) {
        storage.remove(adminId);
    }
}
