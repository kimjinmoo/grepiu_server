package com.iuom.springboot.process.sample.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestMongoDBCrawler extends MongoRepository<Crawler, String> {
}
