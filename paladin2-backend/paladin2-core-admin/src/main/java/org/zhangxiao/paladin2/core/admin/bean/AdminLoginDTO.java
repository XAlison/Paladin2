package org.zhangxiao.paladin2.core.admin.bean;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AdminLoginDTO {

    @NotBlank(message = "账号不能为空")
    @Length(min = 4, max = 20, message = "账号长度必须在4-20位之间")
    public String account;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度必须在6-20位之间")
    public String password;
}
