package com.lawbot.award.configurer.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

//@Configuration
@MapperScan(basePackages = "com.lawbot.award.mapper", sqlSessionFactoryRef = "lawbotSqlSessionFactory")
public class LawbotDataSourceConfig {

	@Bean(name = "lawbotDataSource")
    @ConfigurationProperties("spring.datasource.lawbot")
    public DataSource lawbotDataSource() {
		return DruidDataSourceBuilder.create().build();
    }
	
	@Bean(name = "lawbotTransactionManager")
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(lawbotDataSource());
    }

    @Bean(name = "lawbotSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("lawbotDataSource") DataSource lawbotDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(lawbotDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/lawbot/award/mapper/*.xml"));
        return sessionFactory.getObject();
    }

}

