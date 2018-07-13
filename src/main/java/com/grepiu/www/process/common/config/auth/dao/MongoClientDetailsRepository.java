package com.grepiu.www.process.common.config.auth.dao;

import com.grepiu.www.process.common.config.auth.domain.MongoClientDetails;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface MongoClientDetailsRepository extends MongoRepository<MongoClientDetails, String> {

//  boolean deleteByClientId(String clientId);

//  boolean update(MongoClientDetails mongoClientDetails);

//  boolean updateClientSecret(String clientId, String newSecret);

  Optional<MongoClientDetails> findByClientId(String clientId);
}
