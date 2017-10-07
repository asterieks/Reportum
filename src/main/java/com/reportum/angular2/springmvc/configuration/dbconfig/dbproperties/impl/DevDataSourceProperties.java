package com.reportum.angular2.springmvc.configuration.dbconfig.dbproperties.impl;

import com.reportum.angular2.springmvc.configuration.dbconfig.dbproperties.DataSourceProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@PropertySource("classpath:dev-database.properties")
public class DevDataSourceProperties extends DataSourceProperties {}
