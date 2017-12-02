package com.iuom.springboot.process.api.repository.factory;

import org.mybatis.spring.SqlSessionTemplate;

public interface RoutingSqlSessionTemplateFactory {
    SqlSessionTemplate getSqlSessionTemplate(String id);
}
