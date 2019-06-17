package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.entity.Post;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  @Query(value = "{}", fields = "{'content': 0}")
  Page<Post> findAllExcludeContent(Pageable pageable);

  @Query(value = "{'subject':{$regex:?0, $options:'i'}, 'hashTag': { $in: [?0]}}", fields = "{'content': 0}")
  Page<Post> findBySubjectLikeAndHashTagLike(String subject, List hashTag, Pageable pageable);

  @Query(value = "{'subject':{$regex:?0,$options:'i'}}", fields = "{'content': 0}")
  Page<Post> findBySubjectLike(String subject, Pageable pageable);

  @Query(value = "{'hashTag': {$in: [?0]}}", fields = "{'content': 0}")
  Page<Post> findByHashTagLike(List hashTag, Pageable pageable);

  Long countByHashTag(String hashTagName);
}
