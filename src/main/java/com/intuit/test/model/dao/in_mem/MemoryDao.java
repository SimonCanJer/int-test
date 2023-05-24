package com.intuit.test.model.dao.in_mem;

import com.intuit.test.model.dao.api.IDao;
import com.intuit.test.model.entities.Player;
import com.intuit.test.source_read.api.IReader;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * DAO which keeping data in memory as map(id->player) and
 * simply returning object as a key of the map.
 */
@Slf4j
public class MemoryDao implements IDao {
    private final Map<String,Player> mappedPlayers;
    public MemoryDao(IReader<Player> reader){
        try {
            mappedPlayers= reader.readContent();
            log.info("in mem mapping data readen from file succesfully,  mapped= {} entries",mappedPlayers.size());
        } catch (Exception e) {
            log.error("fatal error: cannot read file");
            throw new RuntimeException(e);
        }

    }

    /**
     * implementer of interface methods.
     * returns a copy of values of mapes
     * @return: the values announced
     */
    @Override
    public Collection<Player> allPlayers() {
        return mappedPlayers.values().stream().toList();
    }

    /**
     *  returns  Player by id
     * @param id  the id announced
     * @return    Either a {@link Player} object if found, or null if not.
     */
    @Override
    public Player get(String id) {
        return mappedPlayers.get(id);
    }
}
