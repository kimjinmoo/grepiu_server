package com.iuom.springboot.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class OauthConfig extends WebSecurityConfigurerAdapter  {

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication().withUser(User.withDefaultPasswordEncoder().username("iukim21c").password("iukim21c").roles("USER"));
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
        .formLogin().and().httpBasic()
        .and()
        .logout().logoutUrl("/logout");
  }

//  @Bean
//  public DaoAuthenticationProvider authenticationProvider() {
//    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//    authProvider.setPasswordEncoder(passwordEncoder());
//    return authProvider;
//  }


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }
}
