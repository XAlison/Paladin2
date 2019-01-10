package org.zhangxiao.paladin2.common.util;

import org.springframework.validation.BindingResult;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.common.exception.error.BaseBizError;

public class DTOUtils {
    public static void checkThrow(BindingResult bindingResult) throws BizException {
        if (bindingResult.hasErrors() && bindingResult.getFieldError() != null) {
            throw new BizException(BaseBizError.DTO_INVALID.suffix(bindingResult.getFieldError().getDefaultMessage()));
        }
    }
}
