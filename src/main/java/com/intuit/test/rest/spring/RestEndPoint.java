package com.intuit.test.rest.spring;

import com.intuit.test.model.dao.api.IDao;
import com.intuit.test.model.entities.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller()
@RequestMapping(path="/api")
public class RestEndPoint {

    @Autowired
    IDao dao;

    /**
     * The method is mapped to handle a get request for all players
     *
     * @return  Either collection of {@link Player} when no error occured, or
     *          InternalError when it has been occured/
     */
    @GetMapping("/players")
    ResponseEntity<Collection<Player>> allPlayers(){
        try{
            return ResponseEntity.ok(dao.allPlayers());
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().body(new ArrayList<Player>());
        }
    }

    /**
     * Get a particular player by id mapped
     * @param playerId - identifier of player
     * @return  ResponseEntity, which either contains the searched entity, whe found.
     *          or the NotFound error code.
     */
    @GetMapping("/players/{playerId}")
    ResponseEntity<Player> player(@PathVariable() String playerId){
        try{
            Player player= dao.get(playerId);
            if(player==null)
                return ResponseEntity.notFound().build();
            else{
                return ResponseEntity.ok(player);
            }
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
