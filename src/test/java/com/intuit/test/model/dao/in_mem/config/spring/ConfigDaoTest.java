package com.intuit.test.model.dao.in_mem.config.spring;

import com.intuit.test.model.dao.api.IDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfig.class)
class ConfigDaoTest {
    @Autowired
    IDao dao;

    @Test
    public void test() {

        assertNotNull(dao);
        assertTrue(dao.allPlayers().size() > 0, "size must be not null");

    }

}