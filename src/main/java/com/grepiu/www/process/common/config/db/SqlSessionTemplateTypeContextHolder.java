package com.grepiu.www.process.common.config.db;

public class SqlSessionTemplateTypeContextHolder {

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * Sets the sql session template type
     *
     * @param typeId
     */
    public static void setSqlSessionTemplateTypeId(String typeId) {
        contextHolder.set(typeId);
    }

    /**
     * Gets the sql session template type
     *
     * @return sqlSessionTemplateType
     */
    public static String getSqlSessionTemplateTypeId() {
        return contextHolder.get();
    }

    /**
     * Clear sql session templates
     */
    public static void clearSqlMapClientType() {
        contextHolder.remove();
    }

}
