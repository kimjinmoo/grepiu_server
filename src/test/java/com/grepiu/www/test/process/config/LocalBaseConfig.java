package com.grepiu.www.test.process.config;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 * TDD 설정 extends 후 사용
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("local")
public abstract class LocalBaseConfig {

  /**
   *
   * 초기화 설정
   *
   */
  @Before
  public abstract void setUp();
}
