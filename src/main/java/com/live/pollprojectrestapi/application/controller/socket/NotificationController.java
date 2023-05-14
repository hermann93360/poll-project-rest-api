package com.live.pollprojectrestapi.application.controller.socket;

import com.live.pollprojectrestapi.application.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class NotificationController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/socket/newUser")
    @SendTo("/socket/newUser")
    public UserDto addUser() {
        return new UserDto("dss", "dezds", "ded", "dzef");
    }

}
