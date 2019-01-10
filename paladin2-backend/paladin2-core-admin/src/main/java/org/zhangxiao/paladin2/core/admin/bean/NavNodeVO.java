package org.zhangxiao.paladin2.core.admin.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NavNodeVO {
    private String tag;
    private String title;
    private String path;
    private List<NavNodeVO> children = new ArrayList<>();
}
