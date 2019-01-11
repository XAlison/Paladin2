package org.zhangxiao.paladin2.core.admin.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class RolePermissionDTO {
    List<String> permissionList;


    public boolean hasDuplicatePermission() {
        Set<String> set = new HashSet<>(permissionList);
        return permissionList.size() != set.size();
    }
}
