package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.domain.HashTag;
import com.grepiu.www.process.grepiu.domain.Post;
import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * Post Collection
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

  Page<Post> findBySubjectLikeAndHashTagLike(String subject, List hashTag, Pageable pageable);

  Page<Post> findBySubjectLike(String subject, Pageable pageable);

  Page<Post> findByHashTagLike(List hashTag, Pageable pageable);

  Long countByHashTag(String hashTagName);
}
