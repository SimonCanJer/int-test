package com.intuit.test.model.dao;

import com.intuit.test.model.dao.api.IDao;
import com.intuit.test.model.dao.api.model.entities.IPlayer;
import com.intuit.test.source.csv.api.entities.Player;
import com.intuit.test.source.csv.csvBean.CsvReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
/**
 * tests {@link MemoryDao} class after POJO like instantiation
 */
public class MemoryDaoTest {

    public static final int EXPECTED_RECORDS = 19370;
    public static final int TIMEOUT_MILLIS = 3000;

    @Test

    public void test(){
        MemoryDao dao=new MemoryDao(new CsvReader<>("player.csv", Player.class,(p)->p.getPlayerID()));
        testDao(dao);
    }

    public static void testDao(IDao dao) {
        assertNotNull(dao.get("abadan01"));
    }

    @Test
    public void  testAllPlayerRetrieve(){
        MemoryDao dao=new MemoryDao(new CsvReader<>("player.csv", Player.class,(p)->p.getPlayerID()));
        testDaoAll(dao);
    }
    public static void testDaoAll(IDao dao){
        AtomicInteger counter= new AtomicInteger();
        Flux<IPlayer> player = dao.allPlayers();
        AtomicBoolean completed= new AtomicBoolean(false);
        player.doOnNext((next)->{
            counter.getAndIncrement();
            log.info("flux : next N {}",counter.get());
        }).doOnComplete(()->{
            completed.set(true);

        }).timeout(Duration.ofMillis(TIMEOUT_MILLIS)).subscribe();

        assertEquals(EXPECTED_RECORDS,counter.get());

    }
}