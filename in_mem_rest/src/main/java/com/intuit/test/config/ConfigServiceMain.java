package com.intuit.test.config;

import com.intuit.test.model.dao.MemoryDao;
import com.intuit.test.model.dao.api.IDao;
import com.intuit.test.model.dao.api.model.entities.IPlayer;
import com.intuit.test.source.csv.api.IReader;
import com.intuit.test.source.csv.api.entities.Player;
import com.intuit.test.source.csv.csvBean.CsvReader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans({@ComponentScan("com.intuit.test.controller")})
@Slf4j
public class ConfigServiceMain {
    @Getter
    static class Property{
        /** this is path to file, where read data from */
        @Value("${data.source.file}")
        private String file;
    }
    @Bean
    Property configServiceProps(){
        return new Property();
    }

    /**
     * exports {@link IReader bean}
     * @param props  - export properties
     * @return
     */
    @Bean
    IReader<IPlayer> csvReaderBean(@Autowired Property props){

        log.debug("exporting csv reader bean..");
        return new CsvReader<IPlayer, Player>(props.getFile(), Player.class,(p)->p.getPlayerID() );
    }
    /**
     * bean export method
     * @param reader  autowired {@link IReader<IPlayer>}
     * @return  the announced Dao
     */
    @Bean
    static IDao inMemDao(@Autowired  IReader<IPlayer> reader){
        log.debug("imMemDao creating bean..");
        return new MemoryDao(reader);
    }
}
