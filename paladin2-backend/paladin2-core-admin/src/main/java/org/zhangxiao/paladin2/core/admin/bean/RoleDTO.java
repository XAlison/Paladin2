package org.zhangxiao.paladin2.core.admin.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Accessors(chain = true)
public class RoleDTO {
    @NotBlank(message = "角色名称不能为空")
    private String title;
    private String des;
}
