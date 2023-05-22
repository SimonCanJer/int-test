package com.intuit.test.source_read;

import com.intuit.test.TestConstants;
import com.intuit.test.model.entities.Player;
import com.intuit.test.source_read.csvBean.CsvReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    @Test
    void readContent() {
        CsvReader<Player> reader= new CsvReader<>(TestConstants.CSV_SOURCE,Player.class,(p)->p.getPlayerID());
        try {
            Map<String, Player> players = reader.readContent();
            assertTrue(players.size()>0);
            Player p=players.get("mauldma01");
            assertNotNull(p);
            assertEquals(1914,p.getBirthYear());

        }
        catch(Exception e){
            assertNull(e,"exception thrown "+e.getMessage());
        }

    }
}