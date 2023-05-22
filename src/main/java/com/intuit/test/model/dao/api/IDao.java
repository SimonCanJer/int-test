package com.intuit.test.model.dao.api;

import com.intuit.test.model.entities.Player;

import java.util.Collection;

public interface IDao {

    Collection<Player> allPlayers();
    Player  get(String id);
}
