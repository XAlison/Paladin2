package org.zhangxiao.paladin2.core.exception.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.util.StringUtils;
import org.zhangxiao.paladin2.core.exception.error.IBizError;

/**
 * 业务异常信息
 */

@Getter
public enum BizError implements IBizError {
    //通用
    DTO_INVALID                 (100000, "参数错误"),
    ;

    private int code;
    private String message;

    BizError(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    private IBizError change(String extMessage, boolean isSuffix) {
        if (!StringUtils.isEmpty(extMessage)) {
            int code = this.getCode();
            String message = this.getMessage();

            return new IBizError() {
                @Override
                public int getCode() {
                    return code;
                }

                @Override
                public String getMessage() {
                    if (isSuffix) {
                        return message + ":" + extMessage;
                    } else {
                        return extMessage + ":" + message;
                    }
                }
            };
        }
        return this;
    }

    // 给message增加前置文字
    public IBizError suffix(String extMessage) {
        return change(extMessage, true);
    }

    // 给message增加后置文字
    public IBizError prefix(String extMessage) {
        return change(extMessage, false);
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
