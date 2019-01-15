package org.zhangxiao.paladin2.core.admin.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
@Accessors(chain = true)
public class ChangePasswordDTO {
    private String oldPsw;
    @Length(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    private String newPsw;
}
