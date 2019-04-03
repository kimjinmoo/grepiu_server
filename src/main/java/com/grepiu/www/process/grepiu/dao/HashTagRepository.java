package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.entity.HashTag;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

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
