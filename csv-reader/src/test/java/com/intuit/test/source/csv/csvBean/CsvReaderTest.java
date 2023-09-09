package com.intuit.test.source.csv.csvBean;

import com.intuit.test.model.dao.api.model.entities.IPlayer;
import com.intuit.test.source.csv.api.IReader;
import com.intuit.test.source.csv.api.entities.Player;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * tests read from CSV file using manually instantiated class {@link CsvReader}
 * the method {@link CsvReaderTest#performInstanceTest} is planned to be reused
 */
public class CsvReaderTest {
    @Test
    void readContent() {
        CsvReader<IPlayer, Player> reader= new CsvReader<>("player.csv",Player.class,(p)->p.getPlayerID());
        performInstanceTest(reader);

    }

    /**
     * performs test of  read quality using an instantiated instance of {@link IReader}
     * @param reader the instance announced
     */
     public static void performInstanceTest(IReader<IPlayer> reader) {
        try {
            Map<String, IPlayer> players = reader.readContent();
            assertEquals(19370,players.size());
            IPlayer p=players.get("mauldma01");
            assertNotNull(p);
            assertEquals(1914,p.getBirthYear());

        }
        catch(Exception e){
            assertNull(e,"exception thrown "+e.getMessage());
        }
    }


}