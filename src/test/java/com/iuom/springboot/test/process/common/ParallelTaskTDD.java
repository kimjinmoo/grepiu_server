package com.iuom.springboot.test.process.common;

import com.google.common.collect.Maps;
import com.iuom.springboot.common.task.ParallelTask;
import com.iuom.springboot.common.task.ParallelTaskHelper;
import com.iuom.springboot.test.process.config.LocalBaseConfig;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ParallelTaskTDD extends LocalBaseConfig {

  @Autowired
  private ParallelTask getSampleTask1;

  @Autowired
  private ParallelTask getSampleTask2;

  private HashMap<String, Object> params;

  @Before
  public void setUp() {
    params = Maps.newHashMap();
  }

  /**
   *
   * 테스트크를 병렬 처리한다.
   *
   */
  @Test
  public void process(){
    ParallelTaskHelper taskHelper = new ParallelTaskHelper();
    taskHelper.addTask(getSampleTask1, getSampleTask2);
    log.info(" task size : {} ", taskHelper.getTaskSize());
    Map<String, Object> v = taskHelper.run(params);
    log.info(" return : {}", v);
  }
}
