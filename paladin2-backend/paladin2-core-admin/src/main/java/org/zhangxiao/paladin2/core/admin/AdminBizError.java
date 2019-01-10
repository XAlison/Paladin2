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
    ;

    private String scope = "admin";
    private int code;
    private String message;

    AdminBizError(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
