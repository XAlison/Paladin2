package org.zhangxiao.paladin2.api.rest.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.common.util.DTOUtils;
import org.zhangxiao.paladin2.core.admin.bean.AdminDTO;
import org.zhangxiao.paladin2.core.admin.bean.AdminRowVO;
import org.zhangxiao.paladin2.core.admin.service.impl.SysAdminService;

import java.util.List;
import java.util.Optional;

/**
 * 后台管理员接口
 */
@RestController
public class SysAdminCtrl {

    @Autowired
    private SysAdminService sysAdminService;

    @GetMapping("/manage/sys/admin/list")
    public List<AdminRowVO> list() {
        return sysAdminService.getList();
    }

    @PostMapping("/manage/sys/admin/save/{adminId}")
    public void setAdmin(@PathVariable(required = false) Long adminId, @RequestBody @Validated AdminDTO adminDTO, BindingResult bindingResult) throws BizException {
        DTOUtils.checkThrow(bindingResult);
        sysAdminService.saveOne(adminId, adminDTO);
    }

    @GetMapping("/manage/sys/admin/delete/{adminId}")
    public void delAdmin(@PathVariable Long adminId) throws BizException {
        sysAdminService.deleteOne(adminId);
    }

    @GetMapping("/manage/sys/admin/get/{adminId}")
    public AdminRowVO getAdmin(@PathVariable Long adminId) throws BizException {
        return Optional.ofNullable(sysAdminService.getOne(adminId))
                .orElse(new AdminRowVO());
    }
}
