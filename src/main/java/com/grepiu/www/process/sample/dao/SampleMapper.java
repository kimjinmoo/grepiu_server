package com.grepiu.www.process.sample.dao;

import com.grepiu.www.process.common.config.db.DBFactory;
import com.grepiu.www.process.common.config.db.SqlSessionTemplateType;
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
