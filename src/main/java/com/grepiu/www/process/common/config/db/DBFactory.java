package com.grepiu.www.process.common.config.db;

import java.lang.annotation.*;

/**
 *
 * DB 커스텀 인터페이스
 *
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DBFactory {
    SqlSessionTemplateType value() default SqlSessionTemplateType.MARIA_READONLY;
}
