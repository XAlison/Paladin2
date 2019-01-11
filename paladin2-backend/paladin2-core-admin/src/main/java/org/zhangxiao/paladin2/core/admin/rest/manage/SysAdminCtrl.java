package org.zhangxiao.paladin2.core.admin.rest.manage;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.common.util.DTOUtils;
import org.zhangxiao.paladin2.core.admin.bean.AdminDTO;
import org.zhangxiao.paladin2.core.admin.bean.AdminRowVO;
import org.zhangxiao.paladin2.core.admin.bean.UiPermissionVO;
import org.zhangxiao.paladin2.core.admin.service.impl.SysAdminService;
import org.zhangxiao.paladin2.core.admin.service.impl.SysPermissionResourceService;

import java.util.List;
import java.util.Optional;

/**
 * 后台管理员接口
 */
@RestController
public class SysAdminCtrl {

    @Autowired
    private SysAdminService sysAdminService;
    @Autowired
    private SysPermissionResourceService sysPermissionResourceService;

    @GetMapping("/manage/sys/admin/ui_permission")
    public UiPermissionVO uiPermission() {
        Subject subject = SecurityUtils.getSubject();
        return sysPermissionResourceService.getPermittedUIPermission(subject);
    }

    @GetMapping("/manage/sys/admin/list")
    public List<AdminRowVO> list() {
        return sysAdminService.getList();
    }

    @PostMapping("/manage/sys/admin/create")
    public void create(@RequestBody @Validated AdminDTO adminDTO, BindingResult bindingResult) throws BizException {
        DTOUtils.checkThrow(bindingResult);
        sysAdminService.createOne(adminDTO);
    }

    @PostMapping("/manage/sys/admin/{adminId}/update")
    public void setAdmin(@PathVariable(required = false) Long adminId, @RequestBody @Validated AdminDTO adminDTO, BindingResult bindingResult) throws BizException {
        DTOUtils.checkThrow(bindingResult);
        sysAdminService.updateOne(adminId, adminDTO);
    }

    @GetMapping("/manage/sys/admin/{adminId}/delete")
    public void delAdmin(@PathVariable Long adminId) throws BizException {
        sysAdminService.deleteOne(adminId);
    }

    @GetMapping("/manage/sys/admin/{adminId}")
    public AdminRowVO getAdmin(@PathVariable Long adminId) throws BizException {
        return Optional.ofNullable(sysAdminService.getOne(adminId))
                .orElse(new AdminRowVO());
    }
}
