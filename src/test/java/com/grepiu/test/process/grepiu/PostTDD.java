package com.grepiu.test.process.grepiu;

import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.grepiu.dao.PostRepository;
import com.grepiu.www.process.grepiu.dao.PostRepositoryCustom;
import com.grepiu.www.process.grepiu.domain.Post;
import com.grepiu.www.process.grepiu.service.PostService;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

/**
 *
 * Post TDD
 *
 */
@Slf4j
public class PostTDD extends LocalBaseConfig {

  @Autowired
  private PostService postService;

  @Override
  public void setUp() {

  }

  @Test
  public void findHashTagStatistics() {
    log.info(" JAVA : {}", postService.getHashTagStatistics());
    log.info(" JAVA : {}", postService.aggregate().getMappedResults());
  }
}
