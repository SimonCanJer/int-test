package com.intuit.test.model.dao;

import com.intuit.test.model.dao.api.IDao;
import com.intuit.test.model.dao.api.model.entities.IPlayer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link IDao} implementation for Solr search engine.
 */
@Slf4j
public class SolrDao implements IDao {

    private final HttpSolrClient client;
    private final int pageSize;

    public static class Player implements IPlayer {

        @Field()
        @Getter
        private String playerID;

        @Field()
        private long birthYear;

        @Field()
        private long birthMonth = 12;

        @Field()
        private long birthDay;

        @Field()
        @Getter
        private String birthCountry;

        @Field()
        @Getter
        private String birthState;

        @Field()
        @Getter
        private String birthCity;

        @Field
        private long deathYear = -1;

        @Field
        private long deathMonth = -1;

        @Field
        private long deathDay = -1;

        @Field
        @Getter
        private String deathCountry;


        @Field
        @Getter
        private String deathState;

        @Field
        @Getter
        private String deathCity;

        @Field
        @Getter
        private String nameFirst = "Jarry";

        @Field
        @Getter
        private String nameLast = "Clark";

        @Field
        @Getter
        private String nameGiven = "Lightning";

        @Field
        private long weight = 100;

        @Field
        private long height = 200;

        @Field
        private String bats;

        ///@JsonProperty("throws")
        @Field
        private String tthrows;

        @Field()
        private Date debut = Date.from(Instant.now());
        @Field()
        private Date finalGame = Date.from(Instant.now());

        @Field
        @Getter
        private String retroID = "";

        @Field
        @Getter
        private String bbrefID = "";


        @Override
        public int getBirthYear() {
            return (int) birthYear;
        }

        @Override
        public int getBirthMonth() {
            return (int) birthMonth;
        }

        @Override
        public int getBirthDay() {
            return (int) birthDay;
        }

        @Override
        public int getDeathYear() {
            return (int) deathYear;
        }

        @Override
        public int getDeathMonth() {
            return (int) deathMonth;
        }

        @Override
        public int getDeathDay() {
            return (int) deathDay;
        }

        @Override
        public int getWeight() {
            return (int) weight;
        }

        @Override
        public int getHeight() {
            return (int) height;
        }

        @Override
        public Character getBats() {
            return toChar(bats);
        }

        @Override
        public Character getTthrows() {
            return toChar(tthrows);
        }

        @Override
        public java.sql.Date getDebut() {
            return toSqlDate(debut);
        }

        @Override
        public java.sql.Date getFinalGame() {
            return toSqlDate(finalGame);
        }

        static private Character toChar(String s) {
            if (StringUtils.isEmpty(s))
                return null;
            return s.charAt(0);
        }

        private static java.sql.Date toSqlDate(Date standard) {
            return new java.sql.Date(standard.toInstant().toEpochMilli());
        }
    }

    public SolrDao(String solrCore, String solrUrl, int pageSize) {

        this.pageSize = pageSize;
        client = new HttpSolrClient.Builder(String.format("%s/solr/%s", solrUrl, solrCore)).build();
    }

    @Override
    public Flux<IPlayer> allPlayers() {
        AtomicInteger pages= new AtomicInteger();
        return Flux.create((fs)->{
            if(!fs.isCancelled()){
                SolrQuery batchQ = new SolrQuery("playerID:*");
                batchQ.setStart(pages.getAndIncrement()*pageSize);
                batchQ.setRows(pageSize);
                try {
                    List<Player> result= client.query(batchQ).getBeans(Player.class);
                    Iterator<Player> it= result.iterator();
                    while(it.hasNext() && !fs.isCancelled()){
                        fs.next(it.next());
                    }

                } catch (Exception e) {
                  log.error("error in batch query: {}",e.getMessage());
                  fs.error(e);
                }
            }
            if(fs.isCancelled())
                fs.complete();

        });
    }

    @Override
    public IPlayer get(String id) {
        SolrQuery findQ = new SolrQuery(String.format("playerID:%s"));
        try {
            QueryResponse response = client.query(findQ);
            List<Player> res= response.getBeans(Player.class);
            if(res!=null && res.size()>0){
                return res.get(0);
            }
        } catch (Exception e) {
            log.error("error in query {}", e.getMessage());
        }
        return null;
    }
}
