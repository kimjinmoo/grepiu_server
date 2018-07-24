package com.grepiu.www.process.common.config;

import com.grepiu.www.process.common.config.auth.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class Oauth2ServerConfig {

  @Configuration
  @EnableResourceServer
  protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "grepiu";

    @Override
    public void configure(HttpSecurity http) throws Exception {
      http.anonymous().disable()
          .requestMatchers().antMatchers("/api/v1/**")
          .and().authorizeRequests()
          .antMatchers("/api/v1/**").access("#oauth2.hasScope('read')")
          .and()
          .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
      super.configure(resources);
    }
//
//  @Override
//  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//    resources.resourceId(RESOURCE_ID).stateless(false);
//  }

  }

  @Configuration
  @EnableAuthorizationServer
  protected static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    static final String CLIEN_ID = "grepiu-client";
    static final String CLIENT_SECRET = "grepiu-secret";
    static final String GRANT_TYPE_PASSWORD = "password";
    static final String AUTHORIZATION_CODE = "authorization_code";
    static final String REFRESH_TOKEN = "refresh_token";
    static final String IMPLICIT = "implicit";
    static final String SCOPE_READ = "read";
    static final String SCOPE_WRITE = "write";
    static final String TRUST = "trust";
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
    static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private UserDetailsService currentUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
      oauthServer.tokenKeyAccess("permitAll()")
          .checkTokenAccess("isAuthenticated()");
    }

    /**
     *
     * API 요청 클라이언트 정보를 다룬다.
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      /*//    clients.withClientDetails(mongoClientDetailsService);*/
      clients.inMemory()
          .withClient(CLIEN_ID)
          .authorizedGrantTypes("password", "client_credentials", "authorization_code", "refresh_token")
          .authorities(Role.USER.toString())
          .scopes(SCOPE_READ, SCOPE_WRITE)
//        .resourceIds("grepiu")
          .secret(new BCryptPasswordEncoder().encode(CLIENT_SECRET));
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.tokenStore(tokenStore())
          .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
          .authenticationManager(authenticationManager)
          .userDetailsService(currentUserDetailService);
    }

    @Bean
    public TokenStore tokenStore() {
      return new InMemoryTokenStore();
    }

  }
}
