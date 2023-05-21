package com.live.pollprojectrestapi;

import com.live.pollprojectrestapi.domain.model.Email;
import com.live.pollprojectrestapi.domain.service.up.EmailService;
import com.live.pollprojectrestapi.domain.usecase.usersManagement.up.SendMailForCreateGroupUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PollProjectRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollProjectRestApiApplication.class, args);

    }

}
