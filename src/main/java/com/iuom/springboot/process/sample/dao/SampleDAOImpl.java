package com.iuom.springboot.process.sample.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * SampleDAO 구현체
 */
@Repository
public class SampleDAOImpl implements SampleDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public String getSampleData() {
        return sqlSession.selectOne("api.selectTest");
    }
}
