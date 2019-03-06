package com.grepiu.www.process.sample.dao;

import com.grepiu.www.process.sample.entity.TestUser;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

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
