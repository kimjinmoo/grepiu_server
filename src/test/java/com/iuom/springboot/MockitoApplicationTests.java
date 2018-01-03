package com.iuom.springboot;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iuom.springboot.common.job.Task;
import com.iuom.springboot.common.job.TaskUtil;
import com.iuom.springboot.process.sample.service.SampleTaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Mockito를 이용한 Mock TEst
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Slf4j
public class MockitoApplicationTests {

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sampleTaskServiceTest(){
        HashMap<String, Object> returnMap = Maps.newHashMap();
        HashMap<String, Object> params = Maps.newHashMap();
        List<Task> tasks = Lists.newArrayList();
    }

    public Task getSampleTask1(){
        return new Task() {
            @Override
            public void run(Map<String, Object>...returnMap) throws Exception {
                log.debug("getSampleTask1 Task");
                List<Integer> job = Lists.newArrayList();
                IntStream.range(0,1000).forEach(i->{
                    log.debug("getSampleTask1 {} : ", i);
                    job.add(i);
                });
            }
        };
    }

    public Task getSampleTask2(){
        return new Task() {
            @Override
            public void run(Map<String, Object>...params) throws Exception {
                Map<String, Object> returnMap = TaskUtil.getMap(0, params);
                log.debug("getSampleTask2 Task");
                List<Integer> job = Lists.newArrayList();
                IntStream.range(0,10000).forEach(i->{
                    log.debug("getSampleTask2 {} : ", i);
                    job.add(i);
                });
                returnMap.put("getSampleTask2_jobCount", job.size());
            }
        };
    }
}
