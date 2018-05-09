package com.iuom.springboot.process.sample.dao;

import com.iuom.springboot.process.sample.domain.TestUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 *
 * 몽고 DB 데이터 처리
 * ref : https://docs.spring.io/spring-data/mongodb/docs/1.5.2.RELEASE/reference/html/mongo.repositories.html
 *       https://docs.spring.io/spring-data/data-mongo/docs/current/reference/html/#repositories.query-methods.query-property-expressions
 */
public interface TestMongoDBRepository extends MongoRepository<TestUser, String> {
    TestUser findByFirstName(String firstName);
    List<TestUser> findByLastName(String lastName);
}
