package com.iuom.springboot.process.sample.dao;

import com.iuom.springboot.common.base.repository.DBFactory;
import com.iuom.springboot.common.base.repository.RoutingBaseSqlSessionRepository;
import com.iuom.springboot.common.base.repository.SqlSessionTemplateType;

@DBFactory(value = SqlSessionTemplateType.MARIA_READONLY)
public class SampleRepository extends RoutingBaseSqlSessionRepository {

    @Override
    protected String getSqlMapperPrefix() {
        return "";
    }

    /**
     *
     * 데이터를 가져온다.
     *
     * @return
     */
    public String getDate() {
        return selectOne("getDate");
    }

}
