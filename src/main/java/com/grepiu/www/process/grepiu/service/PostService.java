package com.grepiu.www.process.grepiu.service;


import com.google.common.collect.Maps;
import com.grepiu.www.process.common.api.exception.BadRequestException;
import com.grepiu.www.process.common.api.service.BaseService;
import com.grepiu.www.process.grepiu.dao.HashTagRepository;
import com.grepiu.www.process.grepiu.dao.PostRepository;
import com.grepiu.www.process.grepiu.domain.GrepIUSequence;
import com.grepiu.www.process.grepiu.domain.HashTag;
import com.grepiu.www.process.grepiu.domain.Post;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
  private HashTagRepository hashTagRepository;

  @Autowired
  private BaseService baseService;
  /**
   *
   * POST 등록
   *
   * @param post Post 객체
   * @return Post 객체
   */
//  @CacheEvict(cacheNames = "post", allEntries = true)
  public Post savePost(Post post) {
    // Set 시퀀스
    post.setId(baseService.getNextSequence("post"));
    // 시퀀스 문제로 RegDate 서버에서 지정
    post.setRegDate(new Date());
    // 없는 해시태그는 갱신한다.
    post.getHashTag().forEach(v->{
      HashTag ht = new HashTag();
      ht.setName(v);
      hashTagRepository.save(ht);
    });
    // Save
    return (Post) postRepository.save(post);

  }

  /**
   *
   * POST - 수정
   *
   * @param id String 객체
   * @param post Post 객체
   * @return Post 객체
   */
//  @CacheEvict(cacheNames = "post", allEntries = true)
  public Post updatePost(long id, Post post) throws Exception {
    //Get Data
    Post p = Optional.ofNullable(postRepository.findById(id)).orElseThrow(BadRequestException::new);
    //Set Data
    p.setSubject(post.getSubject());
    p.setContent(post.getContent());
    p.setHashTag(post.getHashTag());
    p.setModifyId(post.getModifyId());
    //Save
    return (Post) postRepository.save(p);
  }

  /**
   *
   * 전체 리스트 가져오기
   *
   * @return List<Post> 객체
   */
  public List<Post> findPostAll() {
    return postRepository.findAll();
  }

  /**
   *
   * 페이징 처리 리스트 가져오기
   *
   * @param page 페이지
   * @param size Scale
   * @return HashMap<String, Object>
   */
//  @Cacheable(value = "post", key = "{#page + #size}")
  public HashMap<String, Object> findPostAllPage(int page, int size) {
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
   * @param id Post ID
   * @return Post 객체
   */
  public Post findOneByID(Long id) {
    return (Post) postRepository.findById(id).get();
  }

  /**
   *
   * 이전 Post 정보
   *
   * @param id Post ID
   * @return Post 객체
   */
  public Post findPostPrev(Long id) {
    return postRepository.findFirstByIdLessThanOrderByRegDateDesc(id);
  }

  /**
   *
   * 다음 Post 정보
   *
   * @param id Post ID
   * @return Post 객체
   */
  public Post findPostNext(Long id) {
    return postRepository.findFirstByIdGreaterThanOrderByRegDateAsc(id);
  }

  /**
   *
   * 삭제
   *
   * @param id String 객체
   */
//  @CacheEvict(cacheNames = "post", allEntries = true)
  public void deletePostById(Long id) {
    postRepository.deleteById(id);
  }

  /**
   *
   * 해시태크 추가
   *
   * @param hashTag HashTag 객체
   * @return HashTag 객체
   */
  public HashTag saveHashTag(HashTag hashTag) {
    return hashTagRepository.save(hashTag);
  }

  /**
   *
   * 해시태크 불러온다.
   *
   * @return List<HashTag> 객체
   */
  public List<HashTag> getHashTag() {
    return hashTagRepository.findAll();
  }
}