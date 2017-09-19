package com.iuom.springboot;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Mockito를 이용한 Mock TEst
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MockitoApplicationTests {

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test1(){
        List<String> t1 = new ArrayList<>();
        t1.add("hello");

        assertEquals(2, t1.size());
    }
}
