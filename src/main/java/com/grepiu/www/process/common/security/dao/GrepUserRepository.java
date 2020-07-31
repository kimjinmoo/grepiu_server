package com.grepiu.www.process.common.security.dao;

import com.grepiu.www.process.common.security.entity.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * User 정보
 *
 */
public interface GrepUserRepository extends MongoRepository<User, String> {
  // ID로 User를 찾는다.
  Optional<User> findUserByIdAndActiveTrue(String id);

  // ID 및 password로 User를 찾는다.
  Optional<User> findUserByIdAndAndPasswordHash(String id, String password);
}
