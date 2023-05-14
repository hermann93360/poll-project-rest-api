package com.live.pollprojectrestapi.application.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNullApi;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.validation.constraints.NotNull;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends TextWebSocketHandler implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/socket");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket")
                .setAllowedOrigins("https://polling-project.hkamguin.fr/", "http://192.168.1.6:4200/")
                .withSockJS();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println();
    }

}

