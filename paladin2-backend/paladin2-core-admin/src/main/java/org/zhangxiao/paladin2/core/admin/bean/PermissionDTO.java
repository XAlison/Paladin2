package org.zhangxiao.paladin2.core.admin.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
public class PermissionDTO {

    @NotBlank(message = "权限名称不能为空")
    private String title;
    @NotBlank(message = "权限表达式不能为空")
    private String permission;
    private Integer sort = 99;
    private String path;
}
