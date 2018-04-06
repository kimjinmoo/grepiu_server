package com.iuom.springboot.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * Spring Security 설정
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

  @Autowired
  private UserDetailsService currentUserDetailService;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // 일반적인 Open 정책
        .antMatchers("/resources/**/*", "/webjars/**", "/ws/**/*", "/app/**", "/topic/messages").permitAll()
        .antMatchers("/", "/home").permitAll()
        .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
            "/configuration/security", "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security", "/null/**").permitAll()
        .antMatchers("swagger-ui.html").hasRole("USER")
        .anyRequest().authenticated()
        .and()
        .formLogin().usernameParameter("email").defaultSuccessUrl("/", true).permitAll()
        .and().logout().logoutSuccessUrl("/").permitAll()
        .and().httpBasic()
        .and().rememberMe();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(currentUserDetailService)
    .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
