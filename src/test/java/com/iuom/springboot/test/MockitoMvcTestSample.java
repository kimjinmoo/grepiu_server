package com.iuom.springboot.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.iuom.springboot.process.sample.domain.TestMongoDBRepository;
import com.iuom.springboot.process.sample.domain.TestUser;
import com.iuom.springboot.process.sample.controller.SampleRestController;
import org.apache.catalina.filters.CorsFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 *
 * MVC 테스트
 *
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MockitoMvcTestSample {

    private MockMvc mockMvc;

    @Mock
    private TestMongoDBRepository testMongoDBRepository;

    @InjectMocks
    private SampleRestController sampleRestController;

    /**
     *
     * 초기화
     *
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sampleRestController).addFilters(new CorsFilter()).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    /**
     *
     * Get Method Tes
     *
     * @throws Exception
     */
    @Test
    public void getMethod() throws Exception {
        //given
        TestUser sample = new TestUser("iukim21c", "kim", "jinmoo","iukim21c@gmail.com");
        //when
        Mockito.when(testMongoDBRepository.findByFirstName("kim")).thenReturn(sample);
        //then
        MvcResult mvcResult = mockMvc.perform(get("/sample/mongodb/users/{firstName}","kim"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Mockito.verify(testMongoDBRepository, Mockito.times(1)).findByFirstName("kim");
    }

    /**
     *
     * Post method Test
     *
     * @throws Exception
     */
    @Test
    public void postMethod() throws Exception {
        //given
        TestUser sample = new TestUser("iukim21c", "kim", "jinmoo","iukim21c@gmail.com");
        //when
        Mockito.when(testMongoDBRepository.save(sample)).thenReturn(sample);
        MvcResult mvcResult = mockMvc.perform(
            post("/sample/mongodb/users").param("id", "iukim21c").param("firstName", "kim")
                .param("lastName", "jinmoo").param("email", "iukim21c@gmail.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        //then
        Mockito.verify(testMongoDBRepository, Mockito.times(1)).save(sample);
    }
}
