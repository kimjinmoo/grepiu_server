package com.iuom.springboot.process.api.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ApiDAO 구현체
 */
@Repository
public class ApiDAOImpl implements ApiDAO{

    @Autowired
    private SqlSession sqlSession;

    @Override
    public String getSampleData() {
        return sqlSession.selectOne("api.selectTest");
    }
}
