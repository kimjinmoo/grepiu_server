package com.grepiu.www.process.common.config;

import com.grepiu.www.process.common.security.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
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
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

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
  protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "grepiu";

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
          .requestMatchers().antMatchers("/grepiu/lab/root/**")
          .and()
          .antMatcher("/users/me")
          .authorizeRequests()
          .antMatchers("/grepiu/lab/root/**").access("#oauth2.hasScope('write')")
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

    // 클라이언트 id
    static final String CLIENT_ID = "grepiu-client";
    // 클라이언트 secret 코드
    static final String CLIENT_SECRET = "grepiu-secret";
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
    // 토큰 시간
    static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;
    // Refresh 토큰 시간
    static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    private UserDetailsService currentUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
          .checkTokenAccess("isAuthenticated()");
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
          .authorizedGrantTypes(GRANT_TYPE_PASSWORD, GRANT_TYPE_CLIENT_CREDENTIALS,
              GRANT_TYPE_AUTHORIZATION_CODE, GRANT_TYPE_IMPLICIT, GRANT_TYPE_REFRESH_TOKEN)
          .authorities(Role.USER.toString(), Role.ADMIN.toString(), Role.SUPER_ADMIN.toString())
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
      endpoints.tokenStore(tokenStore())
          .authenticationManager(authenticationManager)
          .userDetailsService(currentUserDetailService);
    }

    @Bean
    public TokenStore tokenStore() {
      RedisTokenStore redisTokenStore = new RedisTokenStore(jedisConnectionFactory);
      redisTokenStore.setPrefix("grepiu-user-token:");
      return redisTokenStore;
    }
  }
}