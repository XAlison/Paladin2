package org.zhangxiao.paladin2.core.admin.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;

@Getter
@Setter
public class UiPermissionVO {
    private HashSet<String> uiPaths;
    private HashSet<String> uiElements;
    private List<NavNodeVO> uiNavs;
}
