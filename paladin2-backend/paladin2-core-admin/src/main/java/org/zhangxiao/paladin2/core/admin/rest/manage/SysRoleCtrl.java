package org.zhangxiao.paladin2.core.admin.rest.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.core.admin.entity.SysRole;
import org.zhangxiao.paladin2.core.admin.service.impl.SysRolePermissionService;
import org.zhangxiao.paladin2.core.admin.service.impl.SysRoleService;

import java.util.List;

@RestController
public class SysRoleCtrl {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @GetMapping("/manage/sys/role/list")
    public List<SysRole> roleList() {
        return sysRoleService.getList();
    }

    // @PostMapping("/manage/sys/role/save")
    // public void saveRole(@RequestParam(value = "roleId", required = false) Long roleId
    //         , @RequestBody @Validated RoleDTO roleDTO
    //         , BindingResult bindingResult) throws BizException {
    //     DtoUtils.checkThrow(bindingResult);
    //     if (roleId == null) {
    //         sysRoleService.createRole(roleDTO);
    //     } else {
    //         sysRoleService.updateRole(roleId, roleDTO);
    //     }
    // }
    //
    // @GetMapping("/manage/sys/role/delete/{roleId}")
    // public void delete(@PathVariable Long roleId) {
    //     sysRoleService.deleteRole(roleId);
    // }
    //
    // @PostMapping("/manage/sys/role/permission/save")
    // public void permissionSave(@RequestBody @Validated RolePermissionDTO dto, BindingResult bindingResult) throws BizException {
    //     DtoUtils.checkThrow(bindingResult);
    //     sysRolePermissionService.save(dto);
    // }
    //
    // @GetMapping("/manage/sys/role/permission/{roleId}")
    // public List<String> permissionSave(@PathVariable Long roleId) {
    //     return sysRolePermissionService.getPermissionList(roleId);
    // }
}
