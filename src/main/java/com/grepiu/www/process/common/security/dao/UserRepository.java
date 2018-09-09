package com.grepiu.www.process.common.security.dao;

import com.grepiu.www.process.common.security.domain.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * User 정보
 *
 */
public interface UserRepository extends MongoRepository<User, String> {
  // 이메일로 User를 찾는다.
  Optional<User> findUserById(String id);
}
