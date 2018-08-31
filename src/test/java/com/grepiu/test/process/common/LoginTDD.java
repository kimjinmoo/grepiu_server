package com.grepiu.test.process.common;


import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.common.config.auth.dao.UserRepository;
import com.grepiu.www.process.common.config.auth.domain.Role;
import com.grepiu.www.process.common.config.auth.domain.User;
import com.grepiu.www.process.common.config.auth.service.UserService;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

@Slf4j
public class LoginTDD extends LocalBaseConfig {

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Value("${grepiu.oauth.token}")
  private String tokenUrl;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public void setUp() {
  }

  @Test
  public void searchId() {
    Optional<User> user = userRepository.findUserById("iukim21c@gmail.com");
    if(user.isPresent()) {
      log.debug("user : {} ", user.get());
    }
  }

  @Test
  public void createUser() {
    //given
    User user = User.build("iukim21c@gmail.com","xptmxm1!@", Role.USER);
    //when
    userRepository.save(user);
    Optional<User> userCheck = userService.findUserById("iukim21c@gmail.com");
    //then
    Assert.assertTrue(userCheck.isPresent());
  }

  @Test
  public void login() {
    ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
    resourceDetails.setAccessTokenUri(tokenUrl);
    resourceDetails.setClientId("grepiu-client");
    resourceDetails.setClientSecret("grepiu-secret");
//    resourceDetails.setScope(Arrays.asList("read,write"));
    resourceDetails.setGrantType("password");
    resourceDetails.setUsername("iukim21c");
    resourceDetails.setPassword("xptmxm1!");
    OAuth2RestTemplate oAuthRestTemplate = new OAuth2RestTemplate(resourceDetails);

    OAuth2AccessToken token = oAuthRestTemplate.getAccessToken();
    OAuth2RefreshToken refreshToken = token.getRefreshToken();

    log.info("token : {}/{} ", token,refreshToken);
  }
}
