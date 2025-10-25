// ChatController.java
package com.example.websocket_demo;

import com.example.websocket_demo.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/send") // client sends message to /app/send
    @SendTo("/topic/messages") // broadcast to all subscribers of /topic/messages
    public ChatMessage sendMessage(ChatMessage message) {
        return message; // simple echo
    }
}
