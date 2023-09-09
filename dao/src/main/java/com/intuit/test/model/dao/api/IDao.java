package com.intuit.test.model.dao.api;

import com.intuit.test.model.dao.api.model.entities.IPlayer;
import reactor.core.publisher.Flux;

public interface IDao {

    Flux<IPlayer> allPlayers();
    IPlayer get(String id);
}
