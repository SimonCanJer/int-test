package com.intuit.test.source_read.config_spring;

import com.intuit.test.model.entities.Player;
import com.intuit.test.source_read.api.IReader;
import com.intuit.test.source_read.csvBean.CsvReader;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * This class configures source reader to
 * read a csv file from source, which is names in a related property
 */
@Configuration
public class ConfigReaders {

    /** this class is necessary because direct injection of a value not works always
     * when exporting beans
     */
    @Getter
    static class Property{
        /** this is path to file, where read data from */
        @Value("${data.source.file}")
        private String file;
    }
    @Bean
    Property props(){
        return new Property();
    }

    /**
     * exports {@link IReader bean}
     * @param props  - export properties
     * @return
     */
    @Bean
    IReader<Player>  csvReaderBean(@Autowired Property props){
        return new CsvReader<Player>(props().getFile(),Player.class,(p)->p.getPlayerID() );
    }
}
