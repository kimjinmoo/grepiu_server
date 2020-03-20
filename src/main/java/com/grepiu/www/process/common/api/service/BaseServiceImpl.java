package com.grepiu.www.process.common.api.service;

import com.google.common.collect.Maps;
import com.grepiu.www.process.common.api.dao.FileRepository;
import com.grepiu.www.process.common.api.entity.Files;
import com.grepiu.www.process.common.api.domain.LoginForm;
import com.grepiu.www.process.common.api.domain.UserPasswordUpdateForm;
import com.grepiu.www.process.common.api.exception.BadRequestException;
import com.grepiu.www.process.common.security.dao.UserRepository;
import com.grepiu.www.process.common.security.entity.User;
import com.grepiu.www.process.common.api.exception.LoginErrPasswordException;
import com.grepiu.www.process.common.security.service.UserService;
import com.grepiu.www.process.grepiu.entity.GrepIUSequence;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 기본 서비스
 */
@Slf4j
@Service
public class BaseServiceImpl implements BaseService {

    @Value("${grepiu.oauth.token}")
    private String tokenUrl;

    private final TokenStore tokenStore;

    private final UserService userService;

    private final UserRepository userRepository;

    private final MongoOperations mongoOperations;

    private final FileRepository fileRepository;

    public BaseServiceImpl(TokenStore tokenStore,
        UserService userService, UserRepository userRepository, MongoOperations mongoOperations,
        FileRepository fileRepository) {
        this.tokenStore = tokenStore;
        this.userService = userService;
        this.userRepository = userRepository;
        this.mongoOperations = mongoOperations;
        this.fileRepository = fileRepository;
    }

    /**
     *
     * mongoDB 순번 만들기
     *
     * @param seqName
     * @return 순번
     */
    @Override
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
    @Override
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
            r.put("refreshToken", token.getRefreshToken().getValue());
            r.put("expiresIn", token.getExpiresIn());

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
    @Override
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
     * 비밀번호를 초기화 한다. 기본 xptmxm1!
     *
     * @param id
     * @return
     */
    @Override
    public Object resetPassword(String id) {
        return userService.updatePassword(id, "xptmxm1!");
    }

    /**
     *
     * 토큰 유효 체크
     *
     * @param accessToken 토큰 정보
     * @return Object 객체
     */
    @Override
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
    @Override
    public User signUp(User user) {
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
    public User changePassword(UserPasswordUpdateForm form) throws Exception {
        return userService.updatePassword(form);
    }
    /**
     *
     * 유저들 정보를 가져온다.
     *
     * @return List<User>
     */
    @Override
    public List<User> getUsers()  {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.findUserById(id);
    }

    /**
     *
     * 유저틀 탈퇴 시킨다.
     *
     * @param authentication Authentication 객체
     * @return 삭제된 ID
     */
    @Override
    public Object leaveUser(Authentication authentication) throws Exception {
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

    /**
     *
     * 파일 업로드 파일 리스트
     *
     * @return List<Files>;
     */
    @Override
    public List<Files> getUploadFileList() {
        return fileRepository.findAll();
    }

    @Override
    public Object refreshToken(String refreshToken) {

        RestTemplate restTemplate = new RestTemplate();
        LinkedHashMap<String, Object> r = Maps.newLinkedHashMap();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(("grepiu-client:grepiu-secret").getBytes()));

        HttpEntity<?> httpEntity = new HttpEntity<Object>(formData, headers);

        ResponseEntity<Map> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, httpEntity, Map.class);
        OAuth2AccessToken newAccessToken = DefaultOAuth2AccessToken.valueOf(response.getBody());

        r.put("new_access_token", newAccessToken.getValue());
        r.put("expiresIn", newAccessToken.getExpiresIn());
        return r;
    }
}