package com.reportum.angular2.springmvc.configuration.dbconfig.dbproperties.impl;

import com.reportum.angular2.springmvc.configuration.dbconfig.dbproperties.DataSourceProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
@PropertySource("classpath:prod-database.properties")
public class ProdDataSourceProperties extends DataSourceProperties {}
