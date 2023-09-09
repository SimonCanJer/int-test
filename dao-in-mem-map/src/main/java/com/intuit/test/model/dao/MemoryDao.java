package com.intuit.test.model.dao;

import com.intuit.test.model.dao.api.IDao;
import com.intuit.test.model.dao.api.model.entities.IPlayer;
import com.intuit.test.source.csv.api.IReader;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;


import java.util.Iterator;
import java.util.Map;

/**
 * DAO which keeping data in memory as map(id->player) and
 * simply returning object as a key of the map.
 */
@Slf4j
public class MemoryDao implements IDao {
    private final Map<String, IPlayer> mappedPlayers;

    public MemoryDao(IReader<IPlayer> reader) {
        try {
            mappedPlayers = reader.readContent();
            log.info("in mem mapping data readen from file succesfully,  mapped= {} entries", mappedPlayers.size());
        } catch (Exception e) {
            log.error("fatal error: cannot read file");
            throw new RuntimeException(e);
        }
    }

    /**
     * implementer of interface methods.
     * returns a copy of values of mapes
     *
     * @return: the values announced
     */
    @Override
    public Flux<IPlayer> allPlayers() {
        return Flux.create((fs) -> {
            Iterator<IPlayer> it = mappedPlayers.values().iterator();
            while (it.hasNext() &&!fs.isCancelled()){
                fs.next(it.next());
            }
            fs.complete();
        });

    }

    /**
     * returns  Player by id
     *
     * @param id the id announced
     * @return Either a {@link IPlayer} object if found, or null if not.
     */
    @Override
    public IPlayer get(String id) {
        return mappedPlayers.get(id);
    }
}
