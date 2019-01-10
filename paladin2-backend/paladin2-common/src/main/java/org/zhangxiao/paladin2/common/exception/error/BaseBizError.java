package org.zhangxiao.paladin2.common.exception.error;

import lombok.Getter;

/**
 * 业务异常信息
 */

@Getter
public enum BaseBizError implements IBizError {
    //通用
    DTO_INVALID                         (100000, "参数错误"),
    ;
    private String scope = "common";
    private int code;
    private String message;

    BaseBizError(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

}
