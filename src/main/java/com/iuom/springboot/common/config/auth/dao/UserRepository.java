package com.iuom.springboot.common.config.auth.dao;

import com.iuom.springboot.common.config.auth.domain.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * MongoDB 유저 등록
 *
 */
public interface UserRepository extends MongoRepository<User, String> {
  // 이메일로 User를 찾는다.
  Optional<User> findUserByEmail(String email);
}
