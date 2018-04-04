//package com.iuom.springboot.common.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//
///**
// *
// *
// * Oauth 인증 설정
// *
// */
//@Configuration
//@EnableAuthorizationServer
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {
//
//  @Autowired
//  private TokenStore tokenStore;
//
//  @Autowired
//  private AuthenticationManager authenticationManager;
//
//  @Autowired
//  private UserDetailsService currentUserDetailService;
//
//  @Override
//  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//    security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
//  }
//
//  @Override
//  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//    clients.inMemory().withClient("client_id").secret("secret_id")
//        .authorizedGrantTypes("client_password", "authorization_code", "refresh_token", "implicit")
//        .scopes("read", "write", "trust")
//        .accessTokenValiditySeconds(1*60*60).
//        refreshTokenValiditySeconds(6*60*60);
//  }
//
//  @Override
//  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//    endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager);
//  }
//
//  @Bean
//  public TokenStore tokenStore() {
//    return new InMemoryTokenStore();
//  }
//
//}
