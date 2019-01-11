package org.zhangxiao.paladin2.core.admin.rest.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.common.util.DTOUtils;
import org.zhangxiao.paladin2.core.admin.bean.RoleDTO;
import org.zhangxiao.paladin2.core.admin.bean.RolePermissionDTO;
import org.zhangxiao.paladin2.core.admin.entity.SysRole;
import org.zhangxiao.paladin2.core.admin.service.impl.SysRolePermissionService;
import org.zhangxiao.paladin2.core.admin.service.impl.SysRoleService;

import java.util.List;

@RestController
public class SysRoleCtrl {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/manage/sys/role/list")
    public List<SysRole> roleList() {
        return sysRoleService.getList();
    }

    @PostMapping("/manage/sys/role/create")
    public void saveRole(@RequestBody @Validated RoleDTO roleDTO, BindingResult bindingResult) throws BizException {
        DTOUtils.checkThrow(bindingResult);
        sysRoleService.createRole(roleDTO);
    }
    @PostMapping("/manage/sys/role/{roleId}/update")
    public void saveRole(@PathVariable Long roleId, @RequestBody @Validated RoleDTO roleDTO, BindingResult bindingResult) throws BizException {
        DTOUtils.checkThrow(bindingResult);
        sysRoleService.updateRole(roleId, roleDTO);
    }

    @GetMapping("/manage/sys/role/{roleId}/delete")
    public void delete(@PathVariable Long roleId) throws BizException {
        sysRoleService.deleteRole(roleId);
    }

    @PostMapping("/manage/sys/role/{roleId}/permission")
    public void savPermissions(@PathVariable Long roleId,@RequestBody @Validated RolePermissionDTO dto, BindingResult bindingResult) throws BizException {
        DTOUtils.checkThrow(bindingResult);
        sysRoleService.savePermissions(roleId,dto);
    }

    @GetMapping("/manage/sys/role/{roleId}/permission")
    public List<String> getPermissions(@PathVariable Long roleId) throws BizException {
        return sysRoleService.getPermissions(roleId);
    }
}
