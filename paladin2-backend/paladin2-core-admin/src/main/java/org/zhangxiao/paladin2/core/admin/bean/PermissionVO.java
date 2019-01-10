package org.zhangxiao.paladin2.core.admin.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PermissionVO {

    private String permission;
    private String title;
    private String navPath;
    private Integer sort;
    private List<PermissionVO> children = new ArrayList<>();
}
