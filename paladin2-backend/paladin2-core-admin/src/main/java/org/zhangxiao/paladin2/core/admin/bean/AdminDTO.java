package org.zhangxiao.paladin2.core.admin.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
@Accessors(chain = true)
public class AdminDTO {
    @NotBlank(message = "账号不可为空")
    private String account;
    private String password;
    private String nickName;
    private List<Long> roleIdList;
}
