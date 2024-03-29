package org.zhangxiao.paladin2.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Swagger2SpringBoot {

    @Bean
    public Docket manageApiDoc(Swagger2Properties swagger2Properties) {
        Contact contact = new Contact("听风zx", "http://www.zhangxiao.org", "mail@zhangxiao.org");
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("Paladin2接口文档")
                .contact(contact)
                .version("1.0")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.zhangxiao.paladin2.core.admin.rest"))
                .paths(PathSelectors.any())
                .build().enable(swagger2Properties.getCanVisit());
    }
}
