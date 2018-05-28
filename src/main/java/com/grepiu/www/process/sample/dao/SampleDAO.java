package com.grepiu.www.process.sample.dao;

import com.grepiu.www.process.common.config.db.RoutingBaseSqlSessionRepository;
import org.springframework.stereotype.Repository;

/**
 * SampleDAO 구현체
 */
@Repository
public class SampleDAO extends RoutingBaseSqlSessionRepository {

    @Override
    protected String getSqlMapperPrefix() {
        return "api.";
    }

    public String getSampleData() {
        return selectOne("selectTest");
    }
}
