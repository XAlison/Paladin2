package org.zhangxiao.paladin2.core.admin.rest.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhangxiao.paladin2.core.admin.bean.PermissionVO;
import org.zhangxiao.paladin2.core.admin.service.impl.SysPermissionService;

import java.util.List;

@RestController
public class SysPermissionCtrl {
    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping("/manage/sys/permission/tree")
    public List<PermissionVO> getTree() {
        return sysPermissionService.getPermissionTree();
    }

}
