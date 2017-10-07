package com.reportum.angular2.springmvc.configuration.dbconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

public abstract class DataSourceConfig {

    static final String ENTITIES_FOLDER = "com.reportum.angular2.springmvc.persistence.entities";

    protected LocalContainerEntityManagerFactoryBean em;

    public DataSourceConfig(){
        em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan(ENTITIES_FOLDER);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}
