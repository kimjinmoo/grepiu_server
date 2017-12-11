package com.iuom.springboot.common.base.repository.factory;

import org.mybatis.spring.SqlSessionTemplate;

public interface RoutingSqlSessionTemplateFactory {
    SqlSessionTemplate getSqlSessionTemplate(String id);
}
