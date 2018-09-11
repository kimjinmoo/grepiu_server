package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.domain.Post;
import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, Long> {
  Post findById(String id);
}
