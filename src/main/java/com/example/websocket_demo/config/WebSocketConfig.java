package com.example.websocket_demo.config;

import com.example.websocket_demo.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // ❌ REMOVE THESE
    // @Autowired
    // private UserSessionService userSessionService;

    // @Autowired
    // private SimpMessagingTemplate messagingTemplate;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(new CustomHandshakeHandler())
                .addInterceptors(new UserHandshakeInterceptor())
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    // ✔ Keep only this — needed for tracking users in interceptor
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new WebSocketConnectInterceptor());
    }

    // ❌ REMOVE THESE EVENT LISTENERS (they cause circular dependency)
    // @EventListener
    // public void handleWebSocketConnectListener(SessionConnectEvent event) { ... }

    // @EventListener
    // public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) { ... }
}

