// ChatController.java
package com.example.websocket_demo.controller;


import com.example.websocket_demo.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;  // ← add this here

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private SimpUserRegistry userRegistry;


    @MessageMapping("/send") // client sends to /app/send
    public void sendMessage(ChatMessage message) {
        if (message.getReceivers() != null && !message.getReceivers().isEmpty()) {
            // Send to multiple users individually
            for (String receiver : message.getReceivers()) {
                messagingTemplate.convertAndSendToUser(
                        receiver, "/queue/messages", message
                );
            }
        } else {
            // Broadcast if no receivers specified
            messagingTemplate.convertAndSend("/topic/messages", message);
        }
    }
    @MessageMapping("/users") // client sends /app/users
    @SendTo("/topic/users")   // broadcast to all subscribers
    public List<String> getConnectedUsers() {
        // 🔹 Step 1: Collect online users from SimpUserRegistry
        List<String> onlineUsers = userRegistry.getUsers()
                .stream()
                .map(SimpUser::getName)          // username of each connected user
                .filter(name -> name != null)    // 🔹 filter out nulls
                .collect(Collectors.toList());

        // 🔹 Step 2: Log for debugging
        System.out.println("WebSocket online users: " + onlineUsers);

        // 🔹 Step 3: Return the list
        return onlineUsers;
    }

    

}
