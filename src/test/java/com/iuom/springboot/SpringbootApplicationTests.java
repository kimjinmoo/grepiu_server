package com.iuom.springboot;

import com.iuom.springboot.common.util.FilterUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {


	@Autowired
	private SqlSessionFactory sqlSession;

	@Test
	public void contextLoads() {
	}

	//@Test
	public void connectMysql() {
		System.out.println("mysql : " + sqlSession);
    }

    //@Test
    public void listFilter() {
		List<String> lists = Arrays.asList("테스트1","테스트2","테스트3");
		List<String> filterLists = FilterUtils.listFilter(lists, (String o) -> o.contains("1"));

		assertEquals(filterLists.size(),3);
	}
}
