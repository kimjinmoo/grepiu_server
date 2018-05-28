package com.grepiu.www.process.common.config.db.factory;

import org.mybatis.spring.SqlSessionTemplate;

public interface RoutingSqlSessionTemplateFactory {
    SqlSessionTemplate getSqlSessionTemplate(String id);
}
