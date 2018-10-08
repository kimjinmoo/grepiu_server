package com.grepiu.www.process.common.api.service;

import com.google.common.collect.Maps;
import com.grepiu.www.process.common.api.domain.LoginForm;
import com.grepiu.www.process.common.api.domain.UserPasswordUpdateForm;
import com.grepiu.www.process.common.api.exception.BadRequestException;
import com.grepiu.www.process.common.security.dao.UserRepository;
import com.grepiu.www.process.common.security.domain.User;
import com.grepiu.www.process.common.api.exception.LoginErrPasswordException;
import com.grepiu.www.process.common.security.service.UserService;
import com.grepiu.www.process.grepiu.domain.GrepIUSequence;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoOperations mongoOperations;

    /**
     *
     * mongoDB 순번 만들기
     *
     * @param seqName
     * @return 순번
     */
    public long getNextSequence(String seqName)
    {
        GrepIUSequence counter = mongoOperations.findAndModify(
            Query.query(Criteria.where("_id").is(seqName)),
            new Update().inc("seq",1),
            FindAndModifyOptions.options().returnNew(true).upsert(true),
            GrepIUSequence.class);
        return counter.getSeq();
    }

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

    /**
     *
     * 유저 등록
     *
     * @param user User객체
     * @return User 객체
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     *
     * 유저 비밀번호를 변경한다.
     *
     * @param form UserPasswordUpdateForm 객체
     * @return User 객체
     * @throws Exception
     */
    public User updateUser(UserPasswordUpdateForm form) throws Exception {
        return userService.updatePassword(form);
    }
    /**
     *
     * 유저들 정보를 가져온다.
     *
     * @return List<User>
     */
    public List<User> getUsers()  {
        return userRepository.findAll();
    }

    /**
     *
     * 유저틀 탈퇴 시킨다.
     *
     * @param authentication Authentication 객체
     * @return 삭제된 ID
     */
    public Object deleteUser(Authentication authentication) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        // 상세 정보
        Map<String, String> details = Optional.ofNullable(objectMapper
            .convertValue(authentication.getDetails(), Map.class))
            .orElseThrow(BadRequestException::new);

        // 유저 정보
        Map<String, String> principal = Optional.ofNullable(objectMapper
            .convertValue(authentication.getPrincipal(), Map.class))
            .orElseThrow(BadRequestException::new);

        // 토큰 존재 여부 확인
        OAuth2AccessToken token = Optional
            .ofNullable(tokenStore.readAccessToken(details.get("tokenValue")))
            .orElseThrow(BadRequestException::new);
        if(!token.isExpired()){
            userRepository.deleteById(principal.get("username"));
            tokenStore.removeAccessToken(token);
        }
        return principal.get("username");
    }
}