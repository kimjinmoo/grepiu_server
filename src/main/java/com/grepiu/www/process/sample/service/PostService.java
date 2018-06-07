package com.grepiu.www.process.sample.service;


import com.grepiu.www.process.sample.dao.PostRepository;
import com.grepiu.www.process.sample.domain.Post;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
