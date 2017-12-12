package com.iuom.springboot.process.sample.dao;

import com.iuom.springboot.common.base.repository.RoutingBaseSqlSessionRepository;
import com.iuom.springboot.common.base.repository.factory.RoutingSqlSessionTemplateFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * SampleDAO 구현체
 */
@Repository
public class SampleDAO extends RoutingBaseSqlSessionRepository  {

    @Override
    protected String getSqlMapperPrefix() {
        return "api.";
    }

    public String getSampleData() {
        return selectOne("selectTest");
    }
}
