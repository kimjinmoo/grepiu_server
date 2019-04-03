package com.grepiu.test.process.grepiu;

import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.grepiu.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
