package com.iuom.springboot.process.sample.dao;

import com.iuom.springboot.common.db.RoutingBaseSqlSessionRepository;
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
