package com.iuom.springboot.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter  {

  private static final String RESOURCE_ID = "resource_id";

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId(RESOURCE_ID).stateless(false);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests()
        // 일반적인 Open 정책
        .antMatchers("/resources/**/*", "/webjars/**", "/ws/**/*", "/app/**", "/topic/messages")
        .permitAll()
        .antMatchers("/", "/home").permitAll()
        .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
            "/configuration/security", "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security", "/swagger-ui.html", "/null/**").hasAuthority("USER")
        .anyRequest().authenticated()
        .and()
        .formLogin().usernameParameter("email").permitAll().and()
        .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
  }
}
