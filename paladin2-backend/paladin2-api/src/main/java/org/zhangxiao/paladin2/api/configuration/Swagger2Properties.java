package org.zhangxiao.paladin2.api.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "paladin2.swagger2")
public class Swagger2Properties {
    private Boolean canVisit = false;
}
