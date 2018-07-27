package com.grepiu.www.process.common.config;

import com.google.common.collect.Lists;
import com.grepiu.www.process.common.config.Oauth2ServerConfig.AuthorizationServerConfig;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

/**
 *
 * Oauth 서비스
 *
 */
@Service
public class Oauth2ClientConfig {

  @Value("${grepiu.oauth.token.url}")
  private String tokenUrl;

  /**
   *
   * 유저 토큰 값을 가져온다.
   *
   * @param id
   * @param password
   * @return
   */
  public Object getAccessToken(String id, String password) {
    return tokenUrl;
  }
}
