package com.iuom.springboot.process.api.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Api Mapper
 */
@Mapper
public interface ApiMapper {

    @Select("SELECT NOW()")
    public String getSampleData();
}
