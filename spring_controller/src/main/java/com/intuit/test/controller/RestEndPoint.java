package com.intuit.test.controller;

import com.intuit.test.api.DomainLogic;
import com.intuit.test.model.dao.api.IDao;
import com.intuit.test.model.dao.api.model.entities.IPlayer;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

@Controller()
@Slf4j
@RequestMapping(path = "${api.uri:/api}")
public class RestEndPoint extends DomainLogic {

    public static final String PLAYERS = "players";


    @Autowired
    MeterRegistry micrometer;

    private AtomicLong counterAll= new AtomicLong();
    private AtomicLong counterConcrete= new AtomicLong();

    public RestEndPoint(@Autowired IDao dao) {

        super(dao);
    }

    /**
     * init
     * intentioanlly in pack visible
     */
    @PostConstruct
    void init(){
        Gauge.builder(PLAYERS,counterAll::get).
                tag("retrieval","all").
                description("retrieve all query").
                register(micrometer);
        Gauge.builder("usercontroller.usercount",counterConcrete::get).
                tag("retrievel","concrete").
                description("retrieve concrete query").
                register(micrometer);

    }

    /**
     * The method is mapped to handle a get request for all players
     *
     * @return Either collection of {@link IPlayer} when no error occured, or
     * InternalError when it has been occured
     * intentionly in pack visible
     */
    @GetMapping("${api.players.uri:/players}")
    ResponseEntity<Flux<IPlayer>> allPlayers() {
        log.debug("all players request");
        counterAll.incrementAndGet();
        try {
            return ResponseEntity.ok(super.all());
        } catch (Exception e) {
            log.error("exception in all players {} ", e.getMessage());
            return ResponseEntity.internalServerError().body(null);

        }

    }

    /**
     * Get a particular player by id mapped
     *
     * @param playerId - identifier of player
     * @return ResponseEntity, which either contains the searched entity, whe found.
     * or the NotFound error code.
     * intentionly in pack visible
     */
    @GetMapping("${api.players.uri:/players}/{playerId}")
    ResponseEntity<IPlayer> player(@PathVariable() String playerId) {

        log.debug("request for player with id {} ", playerId);
        counterConcrete.incrementAndGet();
        try {
            IPlayer player = super.extract(playerId);
            if (player == null) {
                log.debug("player with id {} is not found ",playerId);
                return ResponseEntity.notFound().build();
            }
            log.debug("player with id {} successfully retrieved ",playerId);
            return ResponseEntity.ok(player);
        } catch (Exception e) {
            log.error("excpetion in player retrieve {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}
