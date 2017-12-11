package com.iuom.springboot.process.sample.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Api Mapper
 */
@Mapper
public interface SampleMapper {

    @Select("SELECT NOW()")
    public String getSampleData();
}
