package com.reportum.angular2.springmvc.configuration.dbconfig.dbproperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class DataSourceProperties {

    @Autowired
    private Environment env;

    public String getDriverName() {
        return env.getProperty("db.driver.name");
    }

    public String getDbUrl() {
        return env.getProperty("db.url");
    }

    public String getUserName() {
        return env.getProperty("db.user.name");
    }

    public String getPassword() {
        return env.getProperty("db.password");
    }
}