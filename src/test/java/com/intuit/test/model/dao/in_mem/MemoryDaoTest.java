package com.intuit.test.model.dao.in_mem;

import com.intuit.test.TestConstants;
import com.intuit.test.model.entities.Player;
import com.intuit.test.source_read.api.IReader;
import com.intuit.test.source_read.csvBean.CsvReader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemoryDaoTest {
    @Test

    public void test(){
        MemoryDao dao=new MemoryDao(new CsvReader<>(TestConstants.CSV_SOURCE, Player.class,(p)->p.getPlayerID()));
        assertNotNull(dao.get("abadan01"));
    }

}