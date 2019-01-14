package org.zhangxiao.paladin2.core.admin.rest.manage;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zhangxiao.paladin2.common.util.StrUtils;
import org.zhangxiao.paladin2.core.admin.shiro.AdminFilter;
import org.zhangxiao.paladin2.core.admin.shiro.storage.AdminPermissionStorage;
import org.zhangxiao.paladin2.core.admin.shiro.AdminToken;
import org.zhangxiao.paladin2.common.exception.BizException;
import org.zhangxiao.paladin2.common.util.DTOUtils;
import org.zhangxiao.paladin2.core.admin.AdminBizError;
import org.zhangxiao.paladin2.core.admin.AdminProperties;
import org.zhangxiao.paladin2.core.admin.bean.AdminLoginDTO;
import org.zhangxiao.paladin2.core.admin.entity.SysAdmin;
import org.zhangxiao.paladin2.core.admin.service.impl.SysAdminService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 后台管理员通行证
 */
@RestController("managePassportCtrl")
public class PassportCtrl {
    @Autowired
    private SysAdminService sysAdminService;
    @Autowired
    private AdminPermissionStorage adminPermissionStorage;
    @Autowired
    private AdminProperties adminProperties;

    @PostMapping("/manage/passport/login")
    public Map<String, String> login(@RequestBody @Validated AdminLoginDTO loginDTO, BindingResult bindingResult) throws BizException {
        DTOUtils.checkThrow(bindingResult);
        String account = loginDTO.getAccount().trim();
        String password = sysAdminService.md5Psw(loginDTO.getPassword());

        SysAdmin admin = new SysAdmin();
        admin.setAccount(account);
        admin.setPassword(password);
        admin = Optional.ofNullable(sysAdminService.getOne(new QueryWrapper<>(admin)))
                .orElseThrow(() -> new BizException(AdminBizError.ADMIN_LOGIN_FAILURE));
        AdminToken adminToken = AdminToken.create(admin.getId(), adminProperties.getJwtSecret());
        Subject subject = SecurityUtils.getSubject();
        subject.login(adminToken);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", adminToken.getJwtStr());
        map.put("nickName", admin.getNickName());
        adminPermissionStorage.remove(admin.getId());
        return map;
    }

    @GetMapping("/manage/passport/logout")
    public void logout(HttpServletRequest request) throws BizException {
        String jwtStr = AdminFilter.getJwtStr(request);
        if (!StrUtils.isEmpty(jwtStr)) {
            AdminToken adminToken = AdminToken.parse(jwtStr, adminProperties.getJwtSecret());
            Long adminId = (Long) adminToken.getPrincipal();
            adminPermissionStorage.remove(adminId);
        }
    }

}
