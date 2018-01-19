package com.iuom.springboot.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iuom.springboot.common.job.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

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
}
