package com.grepiu.www.process.common.security.dao;

import com.grepiu.www.process.common.security.domain.MongoClientDetails;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoClientDetailsRepository extends MongoRepository<MongoClientDetails, String> {

//  boolean deleteByClientId(String clientId);

//  boolean update(MongoClientDetails mongoClientDetails);

//  boolean updateClientSecret(String clientId, String newSecret);

  Optional<MongoClientDetails> findByClientId(String clientId);
}
