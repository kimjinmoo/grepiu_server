package com.grepiu.www.process.sample.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="TestUsers")
public class TestUser {

    @Id
    public String id;

    public String firstName;
    public String lastName;

    @Indexed(unique = true)
    public String email;

    public TestUser(String id, String firstName, String lastName, String email){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    };
}
