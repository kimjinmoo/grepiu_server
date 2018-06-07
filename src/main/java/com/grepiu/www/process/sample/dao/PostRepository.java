package com.grepiu.www.process.sample.dao;

import com.grepiu.www.process.sample.domain.Post;
import java.io.Serializable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository<ID extends Serializable> extends MongoRepository<Post, ID> {
  Post findById(String id);
}
