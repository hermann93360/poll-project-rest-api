package com.live.pollprojectrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PollProjectRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollProjectRestApiApplication.class, args);
    }

}
