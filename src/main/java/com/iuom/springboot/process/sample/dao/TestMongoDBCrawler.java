package com.iuom.springboot.process.sample.dao;

import com.iuom.springboot.common.crawler.domain.Cinema;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 *
 * 몽고 DB
 */
public interface TestMongoDBCrawler extends MongoRepository<Cinema, String> {
    List<Cinema> findAllBy();
}
