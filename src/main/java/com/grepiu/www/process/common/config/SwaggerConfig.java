//package com.grepiu.www.process.common.config;
//
//import java.security.Principal;
//import javax.servlet.ServletContext;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.paths.RelativePathProvider;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.Collections;
//
///**
// *
// *
// * Swagger2 적용
// *
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//    final
//    ServletContext context;
//
//    public SwaggerConfig(ServletContext context) {
//        this.context = context;
//    }
//
//
//    /**
//     *
//     * 샘플 API 적용
//     *
//     * @return
//     */
//    @Bean
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2)
//            .ignoredParameterTypes(Principal.class)
//            .select()
//            .apis(RequestHandlerSelectors.basePackage("com.grepiu.www.process"))
//            .paths(PathSelectors.any()).build()
//            .apiInfo(apiInfo()).pathProvider(new RelativePathProvider(context))
//            .useDefaultResponseMessages(false);
//    }
//
//    /**
//     *
//     * API Information 세팅
//     *
//     * @return
//     */
//    private ApiInfo apiInfo() {
//        return new ApiInfo("IU API",
//                "IU - API Server",
//                "1.0",
//                "",
//                new Contact("김진무", "", "iukim21c@gmail.com"),
//                "",
//                "",
//                Collections.emptyList());
//    }
//}
