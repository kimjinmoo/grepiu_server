package com.grepiu.www.process.common.config.auth.dao;

import com.grepiu.www.process.common.config.auth.domain.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 *
 * MongoDB 유저 등록
 *
 */
public interface UserRepository extends MongoRepository<User, String> {
  // 이메일로 User를 찾는다.
  Optional<User> findUserById(String id);

  ClientDetails findAllBy(String id);
}
