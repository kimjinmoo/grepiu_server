package com.iuom.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 *
 * SpringBootApplication 설정
 * Configuration,EnableAutoConfiguration ComponentScan 어노테이션을 동등하게 호출
 * Configuration - 주입 설정
 * EnableAutoConfiguration - 자동 설정(embed 톰캣 또는 spring-mvc 같은경우 controller.xml에 dispath-서블릿 적용)
 * ComponentScan - 스프링 컨퍼넌트 자동 스캔
 *
 */
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxy
@EnableScheduling
@EnableMongoAuditing

@EnableResourceServer
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.initialize();
		return executor;
	}

}
