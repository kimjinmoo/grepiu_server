package com.grepiu.www.process.sample.task;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.grepiu.www.process.common.tools.task.ParallelTask;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SampleTask {

  @Bean
  public ParallelTask getSampleTask1(){
    return (HashMap<String, Object> params) -> {
      log.debug("getSampleTask1 ParallelTask");
      HashMap<String, Object> result = Maps.newHashMap();

      List<Integer> job = Lists.newArrayList();
      IntStream.range(0,30).forEach(i->{
        log.info("getSampleTask1 {} : ", i);
        job.add(i);
      });
      result.put("task1", job);

      return result;
    };
  }

  @Bean
  public ParallelTask getSampleTask2(){
    return (HashMap<String, Object> params) -> {
      HashMap<String, Object> result = Maps.newHashMap();

      List<Integer> job = Lists.newArrayList();
      IntStream.range(0,25).forEach(i->{
        log.info("getSampleTask2 {} : ", i);
        job.add(i);
      });
      result.put("task2", job);
      return result;
    };
  }
}
