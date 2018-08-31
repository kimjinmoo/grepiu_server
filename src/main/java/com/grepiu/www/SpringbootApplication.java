package com.grepiu.www;

import javax.servlet.MultipartConfigElement;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/**
 *
 * SpringBootApplication 설정
 *   -> Configuration,EnableAutoConfiguration ComponentScan 어노테이션을 동등하게 호출
 *    Configuration - 주입 설정
 *    EnableAutoConfiguration - 자동 설정(embed 톰캣 또는 spring-mvc 같은경우 controller.xml에 dispath-서블릿 적용)
 *    ComponentScan - 스프링 컨퍼넌트 자동 스캔
 * EnableAsync : 비동기 메소드 실행을 가능하게 한다. ref. https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/EnableAsync.html
 * EnableAspectJAutoProxy : AOP 사용하게 한다. ref. https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/EnableAspectJAutoProxy.html
 * EnableScheduling : Spring 스케쥴러를 사용 가능하게 한다. ref. https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/EnableScheduling.html
 * EnableMongoAuditing : 스프링 Data MongoDB 를 사용가능하게 한다. ref. https://docs.spring.io/spring-data/mongodb/docs/1.5.6.RELEASE/reference/html/mongo.core.html
 */
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy
@EnableScheduling
@EnableMongoAuditing
public class SpringbootApplication {

	@Bean
	public ExitCodeGenerator exitCodeGenerator() {
		return () -> 42;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	/**
	 *
	 * 스케쥴 설정 값 설정
	 *
	 * @return
	 */
	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.initialize();
		return executor;
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(-1);
		return factory.createMultipartConfig();
	}

	@Bean("multipartResolver")
	public StandardServletMultipartResolver multipartResolver() {
		StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
		return multipartResolver;
	}
}
