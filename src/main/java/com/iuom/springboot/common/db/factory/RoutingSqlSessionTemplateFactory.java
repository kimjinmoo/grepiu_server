package com.iuom.springboot.common.db.factory;

import org.mybatis.spring.SqlSessionTemplate;

public interface RoutingSqlSessionTemplateFactory {
    SqlSessionTemplate getSqlSessionTemplate(String id);
}
