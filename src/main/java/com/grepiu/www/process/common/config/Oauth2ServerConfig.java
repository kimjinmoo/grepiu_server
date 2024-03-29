package com.grepiu.www.process.common.config;

import com.grepiu.www.process.common.security.domain.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
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

/**
 *
 * Spring Oauth 설정
 *   ref - https://spring.io/projects/spring-security-oauth
 *         https://spring.io/guides/tutorials/spring-boot-oauth2/
 *
 *
 */
@Configuration
public class Oauth2ServerConfig {

  @Configuration
  @EnableResourceServer
  @Order(2)
  protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {

//    private static final String RESOURCE_ID = "grepiu";

    /**
     *
     * grepiu Lab Url 세팅
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
      http
              .anonymous().disable()
              .requestMatchers()
              .antMatchers("/grepiu/cloud/**", "/oauth/users/**")
              .antMatchers(HttpMethod.DELETE, "/grepiu/post/**")
              .antMatchers(HttpMethod.POST, "/grepiu/post/**")
              .antMatchers(HttpMethod.PUT, "/grepiu/post/**")
              .and()
              .authorizeRequests()
              .antMatchers(HttpMethod.DELETE, "/grepiu/post/**").authenticated()
              .antMatchers(HttpMethod.POST, "/grepiu/post/**").authenticated()
              .antMatchers(HttpMethod.PUT, "/grepiu/post/**").authenticated()
              .antMatchers("/grepiu/lab/root/**").access("#oauth2.hasScope('write')")
              .antMatchers("/me").access("#oauth2.hasScope('read')")
              .and()
              .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
      ;
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

    // 클라이언트 id
    static String CLIENT_ID;
    // 클라이언트 secret 코드
    static String CLIENT_SECRET;
    // 인증 방식 Oauth 주석에 남김
    static final String GRANT_TYPE_PASSWORD = "password";
    // 인증 방식 Oauth 주석에 남김
    static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    // 인증 방식 Oauth 주석에 남김
    static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    // 인증 방식 Oauth 주석에 남김
    static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    // 인증 방식 Oauth 주석에 남김
    static final String GRANT_TYPE_IMPLICIT = "implicit";
    // Scope Set
    static final String SCOPE_READ = "read";
    // Scope Set
    static final String SCOPE_WRITE = "write";
    // 토큰 시간 [1주]
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60*60*24*7;
    // Refresh 토큰 시간 [3주]
    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 60*60*24*21;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService currentUserDetailService;

    private final PasswordEncoder passwordEncoder;

    private final TokenStore tokenStore;

    @Value("${grepiu.oauth.id}")
    public void setClientId(String clientId){
      AuthorizationServerConfig.CLIENT_ID = clientId;
    }

    @Value("${grepiu.oauth.secret}")
    public void setClientSecret(String clientSecret){
      AuthorizationServerConfig.CLIENT_SECRET = clientSecret;
    }

    public AuthorizationServerConfig(AuthenticationManager authenticationManager, UserDetailsService currentUserDetailService, PasswordEncoder passwordEncoder, TokenStore tokenStore) {
      this.authenticationManager = authenticationManager;
      this.currentUserDetailService = currentUserDetailService;
      this.passwordEncoder = passwordEncoder;
      this.tokenStore = tokenStore;
    }

    @Value("${grepiu.oauth.login}")
    private String loginUrl;

    /**
     *
     *  보안 제안사항을 정의한다.
     *  tokenKeyAccess /oauth/token 인증 설정
     *  checkTokenAccess /oauth/check_token 인증 설정
     *
     * @param oauthServer AuthorizationServerSecurityConfigurer 객체
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
      oauthServer
          .tokenKeyAccess("permitAll()")
              .checkTokenAccess("permitAll()");
//          .checkTokenAccess("isAuthenticated()");
    }

    /**
     *
     * API 요청 클라이언트 정보를 다룬다.
     *
     * <pre>
     *   ref . https://oauth.net/2/grant-types/
     *   grant_type
     *   Authorization Code : 허가 코드로 엑세스 토큰을 받는다.
     *   Implicit(암목적) : 공개된 클라이언트에게 쉽게 사용된다. 추가 단계 없이 즉시 엑세스 토큰이 발급된다.
     *   Password : 사용자의 ID와 비밀번호로 엑세스 토큰을 발급한다.
     *   Client Credentials :
     *   Device Code :
     *   Refresh Token : refresh 토큰으로 엑세스 토큰을 받는다. 기존 토큰은 제거 된다.
     * </pre>
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      /*//    clients.withClientDetails(mongoClientDetailsService);*/
      clients
          .inMemory()
          .withClient(CLIENT_ID)
          .secret(passwordEncoder.encode(CLIENT_SECRET))
          .scopes(SCOPE_READ, SCOPE_WRITE)
          .authorizedGrantTypes(
              GRANT_TYPE_PASSWORD,
              GRANT_TYPE_CLIENT_CREDENTIALS,
              GRANT_TYPE_AUTHORIZATION_CODE,
              GRANT_TYPE_IMPLICIT,
              GRANT_TYPE_REFRESH_TOKEN)
          .authorities(Role.USER.name(), Role.ADMIN.name(), Role.SUPER_ADMIN.name(), Role.GS_APP.name(), Role.GS_PARTY_APP.name())
          .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
          .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
          .redirectUris(loginUrl);
    }

    /**
     *
     * 승인 및 토큰을 정의 한다.
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints.tokenStore(tokenStore)
          .authenticationManager(authenticationManager)
          .userDetailsService(currentUserDetailService);
    }


  }
}
