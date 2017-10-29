package com.iuom.springboot.process.api.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestMongoDBRepository extends MongoRepository<TestUser, String> {
    public TestUser findByFirstName(String firstName);
    public List<TestUser> findByLastName(String lastName);
}
