package com.grepiu.test.process.config;

import com.grepiu.www.GrepIUApplication;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * TDD 설정 extends 후 사용
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GrepIUApplication.class)
@ActiveProfiles("dev")
public abstract class DevBaseConfig {

  /**
   *
   * 초기화 설정
   *
   */
  @Before
  public abstract void setUp();
}
