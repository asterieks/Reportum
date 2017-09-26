package com.reportum.angular2.springmvc.configuration;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

import static org.junit.Assert.assertNotNull;


public class DataBaseOracleConfigTest {

    @Mock
    private DriverManagerDataSource dataSource;

    @Mock
    private JpaTransactionManager transactionManager;

    @InjectMocks
    private DataBaseOracleConfig dbConfig = new DataBaseOracleConfig();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void exceptionTranslationTest(){
        PersistenceExceptionTranslationPostProcessor actual = dbConfig.exceptionTranslation();
        assertNotNull(actual);
    }

    @Test
    public void transactionManagerTest(){
        EntityManagerFactory emf = Mockito.mock(EntityManagerFactory.class);
        PlatformTransactionManager actual = dbConfig.transactionManager(emf);
        assertNotNull(actual);
    }

    @Test
    public void sessionFactoryTest(){
        LocalSessionFactoryBean actual = dbConfig.sessionFactory();
        assertNotNull(actual);
        assertNotNull(actual.getHibernateProperties());
    }

    @Test
    public void dataSourceTest(){
        DriverManagerDataSource actual = dbConfig.dataSource();
        assertNotNull(actual);
        assertNotNull(actual.getPassword());
        assertNotNull(actual.getUsername());
        assertNotNull(actual.getUrl());
    }

    @Test
    public void entityManagerFactoryTest(){
        LocalContainerEntityManagerFactoryBean actual = dbConfig.entityManagerFactory();
        assertNotNull(actual);
        assertNotNull(actual.getJpaVendorAdapter());
        assertNotNull(actual.getDataSource());
    }
}
