package com.iuom.springboot.process.api.repository;

/**
 *
 * DB 구조체
 * MARIA - 마리아 DB
 * MONGO - 몽고 DB
 *
 */
public enum SqlSessionTemplateType {

    MARIA_READONLY("mariadb_readonly"),

    MONGO_READONLY("mongodb_readonly");

    private String id;

    SqlSessionTemplateType(String id) {
        this.id = id;
    }

    public String getDbName() {
        return id;
    }
}
