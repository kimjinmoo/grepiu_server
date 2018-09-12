package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.domain.HashTag;
import com.grepiu.www.process.grepiu.domain.Post;
import java.io.Serializable;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface PostRepository extends MongoRepository<Post, Long>, PostRepositoryCustom  {

  @Query("{ 'id' : ?0 }")
  Post findById(String id);

  List<Post> findAllByHashTag(String name);


}
