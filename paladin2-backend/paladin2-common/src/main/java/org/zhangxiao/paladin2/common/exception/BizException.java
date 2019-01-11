package org.zhangxiao.paladin2.common.exception;


import org.zhangxiao.paladin2.common.exception.error.IBizError;

import java.util.function.Supplier;

public class BizException extends Exception implements Supplier<BizException> {

    private static final long serialVersionUID = 1572164595152865129L;

    private IBizError error;

    public BizException(IBizError error) {
        super(error.getMessage());
        this.error = error;
    }

    public IBizError getError() {
        return error;
    }

    @Override
    public BizException get() {
        return this;
    }
}
