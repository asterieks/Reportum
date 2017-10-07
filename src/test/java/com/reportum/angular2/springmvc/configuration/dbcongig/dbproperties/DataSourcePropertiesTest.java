package com.reportum.angular2.springmvc.configuration.dbcongig.dbproperties;

import com.reportum.angular2.springmvc.configuration.dbconfig.dbproperties.DataSourceProperties;
import com.reportum.angular2.springmvc.configuration.dbconfig.dbproperties.impl.DevDataSourceProperties;
import com.reportum.angular2.springmvc.configuration.dbconfig.dbproperties.impl.ProdDataSourceProperties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class DataSourcePropertiesTest {

    @Mock
    private Environment env;

    @InjectMocks
    private DataSourceProperties devProps = new DevDataSourceProperties();

    @InjectMocks
    private DataSourceProperties prodProps = new ProdDataSourceProperties();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getterDevEnvTest() throws Exception {
        when(env.getProperty("db.driver.name")).thenReturn("driver");
        when(env.getProperty("db.url")).thenReturn("url");
        when(env.getProperty("db.user.name")).thenReturn("name");
        when(env.getProperty("db.password")).thenReturn("pass");
        assertEquals("driver", devProps.getDriverName());
        assertEquals("url", devProps.getDbUrl());
        assertEquals("name", devProps.getUserName());
        assertEquals("pass", devProps.getPassword());
    }

    @Test
    public void getterProdEnvTest() throws Exception {
        when(env.getProperty("db.driver.name")).thenReturn("driverProd");
        when(env.getProperty("db.url")).thenReturn("urlProd");
        when(env.getProperty("db.user.name")).thenReturn("nameProd");
        when(env.getProperty("db.password")).thenReturn("passProd");
        assertEquals("driverProd", devProps.getDriverName());
        assertEquals("urlProd", devProps.getDbUrl());
        assertEquals("nameProd", devProps.getUserName());
        assertEquals("passProd", devProps.getPassword());
    }
}
