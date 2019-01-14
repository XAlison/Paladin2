package org.zhangxiao.paladin2.core.admin.shiro.storage;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

import java.util.Hashtable;

/**
 * 管理员权限内存缓存
 * 在AdminRealm中获取用户授权数据的时候使用，如果存在，则不重新获取
 */
public class AdminPermissionStorageInMemory implements AdminPermissionStorage{

    private Hashtable<Long, SimpleAuthorizationInfo> storage = new Hashtable<>();

    @Override
    public SimpleAuthorizationInfo get(Long adminId) {
        return storage.getOrDefault(adminId, null);
    }

    @Override
    public void put(Long adminId, SimpleAuthorizationInfo authorizationInfo) {
        storage.put(adminId, authorizationInfo);
    }

    @Override
    public void remove(Long adminId) {
        storage.remove(adminId);
    }
}
