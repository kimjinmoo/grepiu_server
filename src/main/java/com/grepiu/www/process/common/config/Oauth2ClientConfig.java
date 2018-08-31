package com.grepiu.www.process.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.stereotype.Service;

/**
 *
 * Oauth 서비스
 *
 */
@Service
@EnableOAuth2Client
@Slf4j
public class Oauth2ClientConfig {

  @Value("${grepiu.oauth.token}")
  private String tokenUrl;
}
