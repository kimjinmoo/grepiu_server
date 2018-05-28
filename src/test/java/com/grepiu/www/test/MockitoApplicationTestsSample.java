package com.grepiu.www.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Mockito를 이용한 Mock TEST
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@Slf4j
public class MockitoApplicationTestsSample {

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void sampleTaskServiceTest(){
        //gien

        //when

        //then
    }
}
