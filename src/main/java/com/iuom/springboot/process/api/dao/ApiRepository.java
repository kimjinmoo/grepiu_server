package com.iuom.springboot.process.api.dao;

import com.iuom.springboot.process.api.repository.DBFactory;
import com.iuom.springboot.process.api.repository.RoutingBaseSqlSessionRepository;
import com.iuom.springboot.process.api.repository.SqlSessionTemplateType;

@DBFactory(value = SqlSessionTemplateType.MARIA_READONLY)
public class ApiRepository extends RoutingBaseSqlSessionRepository {

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
