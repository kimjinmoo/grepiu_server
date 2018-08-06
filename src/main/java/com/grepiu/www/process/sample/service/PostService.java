package com.grepiu.www.process.sample.service;


import com.google.common.collect.Maps;
import com.grepiu.www.process.common.utils.DateUtil;
import com.grepiu.www.process.sample.dao.PostRepository;
import com.grepiu.www.process.sample.domain.Post;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

/**
 *
 * Post 서비스
 *
 */
@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  /**
   *
   * 등록
   *
   * @param post Post 객체
   * @return
   */
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
