package org.zhangxiao.paladin2.core.admin.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Accessors(chain = true)
public class PermissionResourceDTO {
    @NotBlank(message = "权限表达式不能为空")
    private String permission;
    @NotNull(message = "资源类型不能为空")
    private Integer typeId;
    @NotBlank(message = "资源数据不能为空")
    private String data;
}
