package com.intuit.test.model.dao.in_mem.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans({@ComponentScan("com.intuit.test.source_read.config_spring"),@ComponentScan("com.intuit.test.model.dao.in_mem.config.spring")})
public class TestConfig {

}
