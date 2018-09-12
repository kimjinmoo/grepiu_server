package com.grepiu.www.process.grepiu.service;


import com.google.common.collect.Maps;
import com.grepiu.www.process.grepiu.dao.PostRepository;
import com.grepiu.www.process.grepiu.domain.GrepIUSequence;
import com.grepiu.www.process.grepiu.domain.Post;

import java.util.HashMap;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 *
 * Post 서비스
 *
 */
@Slf4j
@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private MongoOperations mongoOperations;

  public int getNextSequence(String seqName)
  {
    GrepIUSequence counter = mongoOperations.findAndModify(
        Query.query(Criteria.where("_id").is(seqName)),
        new Update().inc("seq",1),
        FindAndModifyOptions.options().returnNew(true).upsert(true),
        GrepIUSequence.class);
    return counter.getSeq();
  }
  /**
   *
   * 등록
   *
   * @param post Post 객체
   * @return
   */
  @CacheEvict(cacheNames = "post", allEntries = true)
  public Post save(Post post) {
    return (Post) postRepository.save(post);
  }

  /**
   *
   * 수정
   *
   * @param id String 객체
   * @param post Post 객체
   * @return Post 객체
   */
  @CacheEvict(cacheNames = "post", allEntries = true)
  public Post update(String id, Post post) {
    Post p = postRepository.findById(id);
    p.setSubject(post.getSubject());
    p.setContent(post.getContent());
    p.setCategory(post.getCategory());
    p.setModifyId("");
    return (Post) postRepository.save(p);
  }

  /**
   *
   * 전체 리스트 가져오기
   *
   * @return List<Post> 객체
   */
  public List<Post> findAll() {
    return postRepository.findAll();
  }

  /**
   *
   * 페이징 처리 리스트 가져오기
   *
   * @param page
   * @return
   */
  @Cacheable(value = "post", key = "{#page + #size}")
  public HashMap<String, Object> findAllPage(int page, int size) {
    HashMap<String, Object> r = Maps.newHashMap();
    Page<Post> p = postRepository.findAll(PageRequest.of(page, size, Direction.DESC, "regDate"));
    r.put("list", p.getContent());
    r.put("tPage", p.getTotalPages());
    r.put("tCount", postRepository.count());
    return r;
  }

  /**
   *
   * 상세 정보 가져오기.
   *
   * @param id String 객체
   * @return Post 객체
   */
  public Post findOneByID(String id) {
    return (Post) postRepository.findById(id);
  }

  /**
   *
   * 삭제
   *
   * @param id String 객체
   */
  public void deleteById(String id) {
    postRepository.deleteById(id);
  }
}
