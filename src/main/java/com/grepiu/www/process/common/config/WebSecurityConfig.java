package com.grepiu.www.process.common.config;

import com.grepiu.www.process.common.config.auth.domain.Role;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
  @Order(Ordered.HIGHEST_PRECEDENCE)
  protected void configure(HttpSecurity http) throws Exception {
//    http.csrf().disable().anonymous().disable().authorizeRequests().antMatchers("/api-docs/**").permitAll();
    http.sessionManagement()
          .maximumSessions(1) // 로그인은 한명만 허용
        .and()
        .and()
//        .cors()
//        .and()
        .csrf()
          .disable()
        .authorizeRequests()
        // 일반적인 Open 정책
        .antMatchers("/api/**","/grepiu/**", "/sample/**", "/signUp", "/resources/**/*", "/webjars/**",
            "/ws/**/*", "/app/**", "/topic/messages", "/v2/api-docs", "/configuration/ui",
            "/swagger-resources",
            "/configuration/security", "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security", "/null/**","/swagger-ui.html*",
            "/oauth/**"
            ).permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
          .loginPage("/login")
          .usernameParameter("id")
          .passwordParameter("passwd")
          .defaultSuccessUrl("/", true)
          .failureUrl("/login?error=true")
         .permitAll()
        .and()
        .logout()
          .logoutSuccessUrl("/login")
          .logoutSuccessUrl("/");
  }

  /**
   *
   * 유저 로그인 설정
   * MongoDB Service 구현
   *
   * @param auth AuthenticationManagerBuilder 객체
   * @throws Exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(currentUserDetailService)
        .passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public FilterRegistrationBean  corsFilter() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(0);
    return bean;
  }
}
