package com.live.pollprojectrestapi.application.socket;

import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SocketNotification {
    private final SimpMessagingTemplate sm;

    public void send(String path, Object o) {
        sm.convertAndSend(path, o);
    }

}
