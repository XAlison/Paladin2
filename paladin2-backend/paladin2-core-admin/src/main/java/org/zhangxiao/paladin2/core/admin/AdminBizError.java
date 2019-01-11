package org.zhangxiao.paladin2.core.admin;

import lombok.Getter;
import org.zhangxiao.paladin2.common.exception.error.IBizError;

/**
 * 业务异常信息
 */

@Getter
public enum AdminBizError implements IBizError {

    ADMIN_USERNAME_EXIST                (100001, "管理员账号，已存在"),
    ADMIN_PASSWORD_REQUIRED             (100002, "管理员密码，不能为空"),
    ADMIN_NOT_EXIST                     (100003, "管理员不存在或已删除"),
    ADMIN_CANNOT_DELETE                 (100004, "该管理员为超级管理员，无法删除"),
    ADMIN_LOGIN_FAILURE                 (100005, "管理员登录失败"),
    ADMIN_TOKEN_CREATE_FAILURE          (100006, "管理员token创建失败"),
    ADMIN_TOKEN_INVALID                 (100007, "管理员token不合法"),
    ROLE_NOT_EXIST                      (100008, "角色不存在或已删除"),
    ROLE_PERMISSION_DUPLICATE           (100009, "角色权限存在重复项"),
    ROLE_IN_USE                         (100010, "角色正在使用中"),
    PERMISSION_NOT_EXIST                (100011, "权限不存在"),
    PERMISSION_HAS_CHILDREN             (100011, "还存在子权限"),
    PERMISSION_RESOURCE_EXIST           (100011, "权限资源不存在"),
    ;

    private String scope = "admin";
    private int code;
    private String message;

    AdminBizError(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
