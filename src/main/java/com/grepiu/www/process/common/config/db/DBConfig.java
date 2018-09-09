package com.grepiu.www.process.common.config.db;

import com.grepiu.www.process.common.config.db.factory.RoutingSqlSessionTemplateFactory;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * 다중 DB 사용을 위해 구현
 *
 */
@Configuration
@EnableTransactionManagement
public class DBConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    @Profile("local")
    public JedisConnectionFactory jedisConnectionFactoryLocal() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("52.78.158.161", 6379);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of("yourRedisPasswordIfAny"));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    @Profile("prod")
    public JedisConnectionFactory jedisConnectionFactoryProd() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("127.0.0.1", 6379);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of("yourRedisPasswordIfAny"));
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource mysqlReadOnlyDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSource mysqlWriteDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public ServiceLocatorFactoryBean serviceLocatorFactoryBean(){
        ServiceLocatorFactoryBean bean = new ServiceLocatorFactoryBean();
        bean.setServiceLocatorInterface(RoutingSqlSessionTemplateFactory.class);
        return bean;
    }

//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(mysqlDataSource());
//        return sessionFactoryBean.getObject();
//    }

    @Bean
    @Primary
    public SqlSessionFactory getSqlReadOnlySessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mysqlReadOnlyDataSource());
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/*.xml"));
        SqlSessionFactory factory = sqlSessionFactoryBean.getObject();

        return factory;
    }

    @Bean
    public SqlSessionFactory getSqlReadWriteSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mysqlWriteDataSource());
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/*.xml"));
        SqlSessionFactory factory = sqlSessionFactoryBean.getObject();

        return factory;
    }

    @Bean(value = "mariaReadOnly", destroyMethod = "clearCache")
    @Primary
    public SqlSessionTemplate mariaReadOnly() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(getSqlReadOnlySessionFactory());
        return sqlSessionTemplate;
    }

    @Bean(value = "mariaWrite", destroyMethod = "clearCache")
    public SqlSessionTemplate mariaWrite() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(getSqlReadWriteSessionFactory());
        return sqlSessionTemplate;
    }
}
