package com.intuit.test.model.dao.api.model.entities;

import java.sql.Date;

public interface IPlayer {
    public String getPlayerID() ;

    public int getBirthYear();

    public int getBirthMonth();
    public int getBirthDay();

    public String getBirthCountry();

    public String getBirthState();

    public String getBirthCity() ;

    public int getDeathYear();

    public int getDeathMonth() ;
    public int getDeathDay() ;


    public String getDeathCountry();

    public String getDeathState();

    public String getDeathCity();
    public String getNameFirst() ;

    public String getNameLast() ;

    public String getNameGiven() ;

    public int getWeight();

    public int getHeight();

    public Character getBats();

    public Character getTthrows() ;

    public Date getDebut();

    public Date getFinalGame() ;

    public String getRetroID() ;

    public String getBbrefID() ;
}
