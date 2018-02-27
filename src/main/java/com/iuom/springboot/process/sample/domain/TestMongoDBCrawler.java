package com.iuom.springboot.process.sample.domain;

import com.iuom.springboot.common.crawler.domain.LotteCinema;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * 몽고 DB
 */
public interface TestMongoDBCrawler extends MongoRepository<LotteCinema, String> {}
