package com.iuom.springboot;

import com.iuom.springboot.process.sample.service.SampleService;
import com.iuom.springboot.process.sample.web.SampleRestController;
import org.apache.catalina.filters.CorsFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * MVC 테스트
 *
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MockitoMvcTest {

    private MockMvc mockMvc;

    @Mock
    private SampleService sampleService;

    @InjectMocks
    private SampleRestController sampleRestController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sampleRestController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    /**
     *
     * 200번 체크
     *
     * @throws Exception
     */
    @Test
    public void responseTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/sample/util/now/time"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(sampleService, Mockito.times(1)).getSampleList();
    }

    /**
     *
     * post 전달 테스트.
     *
     * @throws Exception
     */
//    @Test
    public void mongoAdd() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/sample/mongodb/users")
                .param("id","iukim21c")
                .param("firstName","kim")
                .param("lastName","jinmoo")
                .param("email","iukim21c@naver.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String v = mvcResult.toString();
    }
}
