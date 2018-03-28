package com.iuom.springboot.common.db;

/**
 *
 * DB 구조체
 * MARIA - 마리아 DB
 * MONGO - 몽고 DB
 *
 */
public enum SqlSessionTemplateType {

    MARIA_READONLY("mariaReadOnly"),

    MARIA_WRITE("mariaWrite"),

    MONGO_READONLY("mongoReadOnly");

    private String id;

    SqlSessionTemplateType(String id) {
        this.id = id;
    }

    public String getDbName() {
        return id;
    }
}
