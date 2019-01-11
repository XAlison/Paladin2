package org.zhangxiao.paladin2.core.admin.rest.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.common.util.DTOUtils;
import org.zhangxiao.paladin2.core.admin.bean.PermissionDTO;
import org.zhangxiao.paladin2.core.admin.bean.PermissionResourceDTO;
import org.zhangxiao.paladin2.core.admin.bean.PermissionVO;
import org.zhangxiao.paladin2.core.admin.entity.SysPermissionResource;
import org.zhangxiao.paladin2.core.admin.service.impl.SysPermissionResourceService;
import org.zhangxiao.paladin2.core.admin.service.impl.SysPermissionService;

import java.util.List;

@RestController
public class SysPermissionCtrl {
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysPermissionResourceService sysPermissionResourceService;

    @GetMapping("/manage/sys/permission/tree")
    public List<PermissionVO> getTree() {
        return sysPermissionService.getPermissionTree();
    }


    @PostMapping("/manage/sys/permission/save")
    public void save(@RequestBody @Validated PermissionDTO permissionDTO, BindingResult bindingResult) throws BizException {
        DTOUtils.checkThrow(bindingResult);
        sysPermissionService.saveOne(permissionDTO);
    }

    @GetMapping("/manage/sys/permission/delete/{permission}")
    public void delete(@PathVariable String permission) throws BizException {
        sysPermissionService.deleteOne(permission);
    }

    @GetMapping("/manage/sys/permission/resources/{permission}")
    public List<SysPermissionResource> getResources(@PathVariable String permission) {
        return sysPermissionResourceService.getResourcesByPermission(permission);
    }

    @PostMapping("/manage/sys/permission/resources/create")
    public void createResource(@RequestBody @Validated PermissionResourceDTO dto, BindingResult bindingResult) throws BizException {
        DTOUtils.checkThrow(bindingResult);
        sysPermissionResourceService.createOne(dto);
    }

    @PostMapping("/manage/sys/permission/resources/delete")
    public void deleteResource(@RequestBody @Validated PermissionResourceDTO dto, BindingResult bindingResult) throws BizException {
        DTOUtils.checkThrow(bindingResult);
        sysPermissionResourceService.deleteOne(dto);
    }

}
