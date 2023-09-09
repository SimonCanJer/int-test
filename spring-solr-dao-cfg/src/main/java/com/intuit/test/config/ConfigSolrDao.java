package com.intuit.test.config;

import com.intuit.test.model.dao.SolrDao;
import com.intuit.test.model.dao.api.IDao;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration

public class ConfigSolrDao {
    @Getter
    static final class SolrProperties{
        @Value("${solr.dao.core:players}")
        String core;

        @Value("$solr.dao.url:http://localhost:8993")
        String url;

        @Value("${solr.dao.psize:1000}")
        int    pageSize;

    }
    @Bean
    IDao solrDao(@Autowired SolrProperties properties){
        return new SolrDao(properties.core, properties.url, properties.pageSize);
    }
}
