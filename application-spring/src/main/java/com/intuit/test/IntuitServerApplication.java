package com.intuit.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication()
@ComponentScans({@ComponentScan("com.intuit.test.config"), @ComponentScan("com.intuit.test.controller")
})
public class IntuitServerApplication {
    public static void main(String[] args) {
         SpringApplication.run(IntuitServerApplication.class);
    }
}