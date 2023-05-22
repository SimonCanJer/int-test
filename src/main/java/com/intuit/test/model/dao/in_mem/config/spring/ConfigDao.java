package com.intuit.test.model.dao.in_mem.config.spring;

import com.intuit.test.model.dao.api.IDao;
import com.intuit.test.model.dao.in_mem.MemoryDao;
import com.intuit.test.model.entities.Player;
import com.intuit.test.source_read.api.IReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Spring class exposing  {@link IDao} bean
 * is implemente by the {@link MemoryDao} class
 */
@Configuration
public class ConfigDao {
    /**
     * bean export method
     * @param reader  autowired {@link IReader<Player>}
     * @return  the announced Dao
     */
    @Bean
    static IDao inMemDao(@Autowired  IReader<Player> reader){
        return new MemoryDao(reader);
    }
}
