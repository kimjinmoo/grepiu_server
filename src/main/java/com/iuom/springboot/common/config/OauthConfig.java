package com.iuom.springboot.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class OauthConfig extends WebSecurityConfigurerAdapter  {


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    auth.inMemoryAuthentication()
        .withUser("user")
        .password("password")
        .roles("USER")
        .and()
    .withUser("admin")
        .password("password")
        .roles("ADMIN","USER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/resources/**/*").permitAll()
        .antMatchers("/webjars/**/*").permitAll()
        .antMatchers("/ws/**/*").permitAll()
        .antMatchers("/index.html").permitAll()
        .antMatchers("/swagger-ui.html").hasRole("USER")
        .anyRequest().authenticated()
        .and()
        .formLogin().and().httpBasic();
  }


  private LoginUrlAuthenticationEntryPoint oauth2AuthenticationEntryPoint() {
    return new LoginUrlAuthenticationEntryPoint("/login");
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//    authProvider.setUserDetailsService(userService);
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    // Create an encoder with strength 31
    // values from 4 .. 31 are valid; the higher the value, the more work has to be done to calculate the hash
    return new BCryptPasswordEncoder(12);
  }
}
