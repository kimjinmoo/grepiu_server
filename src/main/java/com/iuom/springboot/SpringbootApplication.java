package com.iuom.springboot;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 *
 * SpringBootApplication 설정
 * Configuration,EnableAutoConfiguration ComponentScan 어노테이션을 동등하게 호출
 * Configuration - 주입 설정
 * EnableAutoConfiguration - 자동 설정(embed 톰캣 또는 spring-mvc 같은경우 web.xml에 dispath-서블릿 적용)
 * ComponentScan - 스프링 컨퍼넌트 자동 스캔
 *
 */
@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	/**
	 * SqlSessionFactory 설정
	 * DB 및 mapper xml 등록
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource)throws Exception{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
		return sessionFactory.getObject();
	}
}
