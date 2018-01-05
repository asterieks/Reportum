package com.reportum.angular2.springmvc.configuration;

import com.reportum.angular2.springmvc.configuration.dbconfig.AppDataSourceConfig;
import com.reportum.angular2.springmvc.configuration.dbconfig.dbproperties.DataSourceProperties;
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


public class AppDataSourceConfigTest {

    @Mock
    private DriverManagerDataSource dataSource;

    @Mock
    private JpaTransactionManager transactionManager;

    @Mock
    private DataSourceProperties env;

    @InjectMocks
    private AppDataSourceConfig dbConfig = new AppDataSourceConfig();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Mockito.when(env.getDriverName()).thenReturn("oracle.jdbc.driver.OracleDriver");
        Mockito.when(env.getDbUrl()).thenReturn("jdbc:oracle:thin:@localhost:1521:XE");
        Mockito.when(env.getUserName()).thenReturn("username");
        Mockito.when(env.getPassword()).thenReturn("password");
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
