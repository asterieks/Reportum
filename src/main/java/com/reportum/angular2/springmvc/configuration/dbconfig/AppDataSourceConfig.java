package com.reportum.angular2.springmvc.configuration.dbconfig;


import com.reportum.angular2.springmvc.configuration.dbconfig.dbproperties.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@Profile({"dev","production"})
@EnableTransactionManagement
public class AppDataSourceConfig extends DataSourceConfig{

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        em.setDataSource(dataSource());
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(dataSourceProperties.getDriverName());
        driverManagerDataSource.setUrl(dataSourceProperties.getDbUrl());
        driverManagerDataSource.setUsername(dataSourceProperties.getUserName());
        driverManagerDataSource.setPassword(dataSourceProperties.getPassword());
        return driverManagerDataSource;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        return properties;
    }
}
