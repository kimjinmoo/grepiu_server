package com.iuom.springboot.process.sample.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 *
 * 몽고 DB 데이터 처리
 *
 */
public interface TestMongoDBRepository extends MongoRepository<TestUser, String> {
    TestUser findByFirstName(String firstName);
    List<TestUser> findByLastName(String lastName);
}
