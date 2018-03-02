package com.iuom.springboot.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 *
 *
 * Swagger2 적용
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     *
     * 샘플 API 적용
     *
     * @return
     */
    @Bean
    public Docket sampleApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.iuom.springboot.process.sample.web"))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo()).useDefaultResponseMessages(false);
    }

    /**
     *
     * API Information 세팅
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfo("IU API",
                "IU - API",
                "1.0",
                "",
                new Contact("김진무", "", "iukim21c@gmail.com"),
                "",
                "",
                Collections.emptyList());
    }
}
