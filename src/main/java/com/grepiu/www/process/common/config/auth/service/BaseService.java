package com.grepiu.www.process.common.config.auth.service;

import com.google.common.collect.Maps;
import com.grepiu.www.process.common.config.auth.domain.LoginForm;
import com.grepiu.www.process.common.config.auth.domain.User;
import com.grepiu.www.process.common.config.auth.exception.LoginErrPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * 기본 서비스
 */
@Slf4j
@Service
public class BaseService {

    @Value("${grepiu.oauth.token}")
    private String tokenUrl;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private UserService userService;

    /**
     *
     * 로그인
     *
     * @param form LoginForm 객체
     * @return Object 객체
     */
    public Object login(LoginForm form) throws LoginErrPasswordException {
        LinkedHashMap<String, Object> r = Maps.newLinkedHashMap();
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setAccessTokenUri(tokenUrl);
        resourceDetails.setClientId("grepiu-client");
        resourceDetails.setClientSecret("grepiu-secret");
        resourceDetails.setGrantType("password");
        resourceDetails.setUsername(form.getId());
        resourceDetails.setPassword(form.getPassword());

        try {
            OAuth2RestTemplate oAuthRestTemplate = new OAuth2RestTemplate(resourceDetails, new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest()));
            oAuthRestTemplate.setAccessTokenProvider(new ResourceOwnerPasswordAccessTokenProvider());

            OAuth2AccessToken token = oAuthRestTemplate.getAccessToken();

            // 유저 정보를 Set한다.
            User user = userService.findUserById(form.getId()).get();

            // 응답값 Set
            r.put("code", HttpStatus.OK.value());
            r.put("id", user.getId());
            r.put("role", user.getRole());
            r.put("accessToken", token.getValue());

        } catch (Exception e) {
            throw new LoginErrPasswordException();
        }
        return r;
    }

    /**
     *
     * 로그 아웃
     *
     * @param accessToken 토큰 정보
     * @return Object 객체
     */
    public Object logout(String accessToken) {
        LinkedHashMap<String, Object> r = Maps.newLinkedHashMap();

        OAuth2AccessToken token = tokenStore.readAccessToken(accessToken);
        tokenStore.removeAccessToken(token);

        // 응닶값 Set
        r.put("code", HttpStatus.OK.value());

        return r;
    }

    /**
     *
     * 토큰 유효 체크
     *
     * @param accessToken 토큰 정보
     * @return Object 객체
     */
    public Object isValidToken(String accessToken) {
        LinkedHashMap<String, Object> r = Maps.newLinkedHashMap();

        OAuth2AccessToken token = tokenStore.readAccessToken(accessToken);

        // 토큰 Set
        r.put("code", HttpStatus.OK.value());
        if(token == null) {
            r.put("isValid", false);
            r.put("message", "유효하지 않은 토큰값입니다.");
        }else if(token.isExpired()) {
            r.put("isValid", false);
            r.put("message", "토큰기간이 만기 되었습니다.");
        } else {
            r.put("isValid", true);
            r.put("message", "정상 적인 토큰 입니다.");
        }

        return r;
    }
}
