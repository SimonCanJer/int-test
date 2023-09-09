package com.intuit.test.api;

import com.intuit.test.model.dao.api.IDao;
import com.intuit.test.model.dao.api.model.entities.IPlayer;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
@Setter
@AllArgsConstructor
public class DomainLogic implements ILogic{
    IDao dao;
    @Override
    public IPlayer extract(String id) {
        return dao.get(id);
    }

    @Override
    public Flux<IPlayer> all() {
        return dao.allPlayers();
    }
}
