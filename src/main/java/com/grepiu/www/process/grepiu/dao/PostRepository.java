package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.domain.HashTag;
import com.grepiu.www.process.grepiu.domain.Post;
import java.io.Serializable;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * 몽고 DB
 * ref : https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
 *
 */
public interface PostRepository extends MongoRepository<Post, Long>, PostRepositoryCustom  {

  @Query("{ '_id' : ?0 }")
  Post findById(long id);

  List<Post> findFirstById(long id, Sort sort);

  Post findFirstByIdGreaterThanOrderByRegDateDesc(long id);

  Post findFirstByIdLessThanOrderByRegDateDesc(long id);

  Post findFirstByIdGreaterThanOrderByRegDateAsc(long id);

  List<Post> findAllByHashTag(String name);


}
