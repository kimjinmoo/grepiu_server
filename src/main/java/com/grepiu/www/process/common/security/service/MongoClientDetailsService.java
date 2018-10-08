package com.grepiu.www.process.common.security.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.grepiu.www.process.common.security.dao.MongoClientDetailsRepository;
import com.grepiu.www.process.common.security.domain.MongoClientDetails;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

/**
 *
 * Oauth에 적용
 * todo. 작업 진행중. 현재는 redis 사용중
 *
 */
@Service
public class MongoClientDetailsService implements ClientDetailsService, ClientRegistrationService {

  @Autowired
  private MongoClientDetailsRepository mongoClientDetailsRepository;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    MongoClientDetails mongoClientDetails = mongoClientDetailsRepository.findByClientId(clientId).orElseThrow(()->
      new ClientRegistrationException(String.format("사용자를 찾을수 없습니다 clientId=%s",clientId))
    );
    return mongoClientDetails;
  }

  @Override
  public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    MongoClientDetails mongoClientDetails = new MongoClientDetails(clientDetails.getClientId(),
        bCryptPasswordEncoder.encode(clientDetails.getClientSecret()),
        clientDetails.getScope(),
        clientDetails.getResourceIds(),
        clientDetails.getAuthorizedGrantTypes(),
        clientDetails.getRegisteredRedirectUri(),
        Lists.newArrayList(clientDetails.getAuthorities()),
        clientDetails.getAccessTokenValiditySeconds(),
        clientDetails.getRefreshTokenValiditySeconds(),
        clientDetails.getAdditionalInformation(),
        getAutoApproveScopes(clientDetails));

  }

  @Override
  public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
//    final MongoClientDetails mongoClientDetails = new MongoClientDetails(clientDetails.getClientId(),
//        clientDetails.getClientSecret(),
//        clientDetails.getScope(),
//        clientDetails.getResourceIds(),
//        clientDetails.getAuthorizedGrantTypes(),
//        clientDetails.getRegisteredRedirectUri(),
//        Lists.newArrayList(clientDetails.getAuthorities()),
//        clientDetails.getAccessTokenValiditySeconds(),
//        clientDetails.getRefreshTokenValiditySeconds(),
//        clientDetails.getAdditionalInformation(),
//        getAutoApproveScopes(clientDetails));
//    final boolean result = mongoClientDetailsRepository.update(mongoClientDetails);
//
//    if (!result) {
//      throw new NoSuchClientException("No such Client Id");
//    }
  }

  @Override
  public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
//    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//    boolean result = mongoClientDetailsRepository.updateClientSecret(clientId, bCryptPasswordEncoder.encode(secret));
//    if (!result) {
//      throw new NoSuchClientException("No such client id");
//    }
  }

  @Override
  public void removeClientDetails(String clientId) throws NoSuchClientException {
//    final boolean result = mongoClientDetailsRepository.deleteByClientId(clientId);
//    if (!result) {
//      throw new NoSuchClientException("No such client id");
//    }
  }

  @Override
  public List<ClientDetails> listClientDetails() {
    final List<MongoClientDetails> allClientDetails = mongoClientDetailsRepository.findAll();
    return Lists.newArrayList(allClientDetails);
  }

  private Set<String> getAutoApproveScopes(final ClientDetails clientDetails) {
    if (clientDetails.isAutoApprove("true")) {
      return Sets.newHashSet("true"); // all scopes autoapproved
    }

    return clientDetails.getScope().stream()
        .filter(clientDetails::isAutoApprove)
        .collect(Collectors.toSet());
  }
}
