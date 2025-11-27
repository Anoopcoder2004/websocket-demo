package com.example.websocket_demo.config;

import com.example.websocket_demo.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

@Component
public class WebSocketConfig {

    @Autowired
    private UserSessionService userSessionService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String username = headers.getFirstNativeHeader("username");
        if(username != null){
            userSessionService.addUser(username);
            messagingTemplate.convertAndSend("/topic/users", userSessionService.getOnlineUsers());
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String username = headers.getUser() != null ? headers.getUser().getName() : null;
        if(username != null){
            userSessionService.removeUser(username);
            messagingTemplate.convertAndSend("/topic/users", userSessionService.getOnlineUsers());
        }
    }
}
