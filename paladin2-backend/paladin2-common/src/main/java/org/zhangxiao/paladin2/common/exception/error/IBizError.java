package org.zhangxiao.paladin2.common.exception.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

public interface IBizError {
    int getCode();

    String getMessage();

    String getScope();


    default IBizError change(String extMessage, boolean isSuffix) {
        if (!StringUtils.isEmpty(extMessage)) {
            int code = this.getCode();
            String message = this.getMessage();
            String scope = this.getScope();

            return new IBizError() {

                @Override
                public String getScope() {
                    return scope;
                }

                @Override
                public int getCode() {
                    return code;
                }

                @Override
                public String getMessage() {
                    if (isSuffix) {
                        return message +  extMessage;
                    } else {
                        return extMessage +  message;
                    }
                }
            };
        }
        return this;
    }

    // 给message增加前置文字
    default IBizError suffix(String extMessage) {
        return change(extMessage, true);
    }

    // 给message增加后置文字
    default IBizError prefix(String extMessage) {
        return change(extMessage, false);
    }

    default String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
