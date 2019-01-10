package org.zhangxiao.paladin2.core.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "paladin2.admin")
public class AdminProperties {
    private String jwtSecret = "admin@jwt@secret=2019-1-10 10:13:03";
    private String passwordSalt = "admin@psw@salt=2019-1-10 12:24:16";
}
