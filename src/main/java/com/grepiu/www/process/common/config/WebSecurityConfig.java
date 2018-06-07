package com.grepiu.www.process.common.config;

import java.util.Arrays;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

  /**
   *
   * CSRF = 크로스 사이트 요청 위조
   * 사이트 내부에서 토큰적용하여 방어하는데
   * API 서버로 이용 하려고 하여 disable 시킴
   *
   * @param http
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
        // 일반적인 Open 정책
        .antMatchers("/sample/**").permitAll()
        .antMatchers("/static/resources/css/resources/**/*", "/webjars/**", "/ws/**/*", "/app/**", "/topic/messages").permitAll()
        .antMatchers("/", "/home").permitAll()
        .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
            "/configuration/security", "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security", "/null/**").permitAll()
        .antMatchers("swagger-ui.html").hasRole("USER")
//        .anyRequest().authenticated()
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
