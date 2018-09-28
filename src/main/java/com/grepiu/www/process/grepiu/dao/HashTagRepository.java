package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.domain.HashTag;
import com.grepiu.www.process.grepiu.domain.Post;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * HashTag Collection
 * ref : https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
 *
 */
public interface HashTagRepository extends MongoRepository<HashTag, String> {

  List<HashTag> findByName(String name);

  Long countByName(String name);
}
