package com.reportum.angular2.springmvc.controller.integrationtest;


import com.reportum.angular2.springmvc.configuration.dbconfig.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Properties;

@Configuration
@Profile({"integration-testX"})
public class IntegrationTestDataSourceConfig extends DataSourceConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        em.setDataSource(dataSource());
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean(name = "dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        driverManagerDataSource.setUrl("jdbc:hsqldb:mem:springmvc");
        driverManagerDataSource.setUsername("sa");
        driverManagerDataSource.setPassword("");
        return driverManagerDataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//        properties.put("hibernate.hbm2ddl.import_files", "import.sql,/import.sql");
//        properties.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
        properties.setProperty("hibernate.show_sql","true");
        return properties;
    }
}
