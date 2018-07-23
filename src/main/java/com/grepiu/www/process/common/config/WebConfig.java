package com.grepiu.www.process.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * Web 설정
 *
 * ref. https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/ContentNegotiationConfigurer.html
 *
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false)    // Path로 response를 구분 옵션 false 처리
        .defaultContentType(MediaType.APPLICATION_JSON_UTF8);
  }
}
