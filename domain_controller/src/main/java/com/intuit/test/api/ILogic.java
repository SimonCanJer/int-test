package com.intuit.test.api;

import com.intuit.test.model.dao.api.model.entities.IPlayer;
import reactor.core.publisher.Flux;

public interface ILogic {

    IPlayer extract(String id);
    Flux<IPlayer> all();
}
