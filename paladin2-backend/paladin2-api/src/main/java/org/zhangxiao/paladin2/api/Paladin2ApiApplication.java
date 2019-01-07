package org.zhangxiao.paladin2.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zhangxiao.paladin2.common.util.SpringUtils;

@MapperScan("org.zhangxiao.paladin2")
@SpringBootApplication
public class Paladin2ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(Paladin2ApiApplication.class, args);
        System.out.println(SpringUtils.getJarDir());
    }

}

