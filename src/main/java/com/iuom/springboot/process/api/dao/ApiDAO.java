package com.iuom.springboot.process.api.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *  Api DAO
 */
public interface ApiDAO {

//    @Select("SELECT NOW()")
    public String getSampleData();
}
