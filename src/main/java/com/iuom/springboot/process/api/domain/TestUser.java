package com.iuom.springboot.process.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="testUser")
public class TestUser {

    @Id
    public String id;

    public String firstName;
    public String lastName;

    public TestUser(){};

    public TestUser(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    };
}
