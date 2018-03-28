package com.iuom.springboot.process.sample.dao;

import com.iuom.springboot.common.base.repository.DBFactory;
import com.iuom.springboot.common.base.repository.SqlSessionTemplateType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Api Mapper
 */
@Mapper
@Repository
@DBFactory(SqlSessionTemplateType.MARIA_READONLY)
public interface SampleMapper{

    @Select("SELECT NOW()")
    public String getSampleData(); 
}
