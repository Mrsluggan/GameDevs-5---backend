package com.gamedevs5.gamedevs5.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("http://localhost:5173/")
public class StompController {
    @MessageMapping("/broadcast")
    @SendTo("/topic/reply")
    public String broadcastMessage(@Payload String message) {
        return "You have received a message: " + message;
    }
    @MessageMapping("/lobby")
    @SendTo("/topic/lobby")
    public String broadcastLobbyMessage(@Payload String message) {
        return message; 
    }

    
}
