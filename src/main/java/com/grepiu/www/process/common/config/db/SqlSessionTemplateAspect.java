package com.grepiu.www.process.common.config.db;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SqlSessionTemplateAspect {
    
    /**
     * Sets the sql session template
     *
     * @param pjp the pjp
     * @param dbFactory the db route
     * @throws Throwable
     */
    @Around(value = "@within(dbFactory) || @annotation(dbFactory)")
    public Object setSqlMapClient(ProceedingJoinPoint pjp, DBFactory dbFactory) throws Throwable {
        Object returnObj = null;

        try {

            if (dbFactory != null) {
                SqlSessionTemplateTypeContextHolder.setSqlSessionTemplateTypeId(dbFactory.value().getDbName());
            } else {
                DBFactory classAnnotation = (DBFactory) pjp.getSignature().getDeclaringType().getAnnotation(DBFactory.class);

                if (classAnnotation != null) {
                    SqlSessionTemplateTypeContextHolder.setSqlSessionTemplateTypeId(classAnnotation.value().getDbName());
                } else {
                    //다 없을땐 default.
                    SqlSessionTemplateTypeContextHolder.setSqlSessionTemplateTypeId(SqlSessionTemplateType.MARIA_READONLY.getDbName());
                }
            }

            returnObj = pjp.proceed();
        } catch (Throwable t) {
            throw t;
        } finally {
            SqlSessionTemplateTypeContextHolder.setSqlSessionTemplateTypeId(SqlSessionTemplateType.MARIA_READONLY.getDbName());
        }

        return returnObj;
    }
}
