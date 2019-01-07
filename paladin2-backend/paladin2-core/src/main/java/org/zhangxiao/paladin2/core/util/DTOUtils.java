package org.zhangxiao.paladin2.core.util;

import org.springframework.validation.BindingResult;
import org.zhangxiao.paladin2.core.exception.BizException;
import org.zhangxiao.paladin2.core.exception.error.BizError;

public class DTOUtils {
    public static void checkThrow(BindingResult bindingResult) throws BizException {
        if (bindingResult.hasErrors() && bindingResult.getFieldError() != null) {
            throw new BizException(BizError.DTO_INVALID.suffix(bindingResult.getFieldError().getDefaultMessage()));
        }
    }
}
