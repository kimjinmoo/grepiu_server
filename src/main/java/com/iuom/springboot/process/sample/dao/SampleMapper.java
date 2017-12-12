package com.iuom.springboot.process.sample.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Api Mapper
 */
@Mapper
@Repository
public interface SampleMapper{

    @Select("SELECT NOW()")
    public String getSampleData();
}
