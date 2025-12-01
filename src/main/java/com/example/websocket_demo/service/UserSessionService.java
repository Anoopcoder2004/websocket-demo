package com.example.websocket_demo.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserSessionService {

    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public void addUser(String username) {
        onlineUsers.add(username);
    }

    public void removeUser(String username) {
        onlineUsers.remove(username);
    }

    // ✅ Return a List instead of Set
    public List<String> getOnlineUsers() {
        return new ArrayList<>(onlineUsers);
    }
}
