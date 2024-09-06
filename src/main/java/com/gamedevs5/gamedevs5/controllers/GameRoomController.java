package com.gamedevs5.gamedevs5.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamedevs5.gamedevs5.dto.Welcome;
import com.gamedevs5.gamedevs5.models.Message;
import com.gamedevs5.gamedevs5.models.User;
import com.gamedevs5.gamedevs5.models.Gameroom.GameRoom;
import com.gamedevs5.gamedevs5.services.GameRoomService;

@RestController()
@RequestMapping("/api/gameroom/")
@CrossOrigin("*")
public class GameRoomController {

    private final GameRoomService gameRoomService;

    public GameRoomController(GameRoomService gameRoomService) {
        this.gameRoomService = gameRoomService;
    }

    // Basic Crudd ----------------------------------
    @GetMapping("{gameRoomID}")
    public ResponseEntity<GameRoom> getGameRoom(@PathVariable("gameRoomID") String gameRoomID) {

        return ResponseEntity.ok().body(gameRoomService.getGameRoomById(gameRoomID));
    }

    @GetMapping("")
    public ResponseEntity<List<GameRoom>> getGameRooms() {
        return ResponseEntity.ok(gameRoomService.getAllGamerooms());
    }

    @PostMapping("create")
    public ResponseEntity<GameRoom> createGameRoom(@RequestBody GameRoom gameRoom) {
        System.out.println("newGameRoom: " + gameRoom.getGameRoomName());
        return ResponseEntity.ok(gameRoomService.createGameRoom(gameRoom));
    }

    @DeleteMapping("{gameRoomID}")
    public ResponseEntity<?> deleteGameRoom(@PathVariable("gameRoomID") String gameRoomID) {
        if (gameRoomService.deleteGameRoom(gameRoomID) == null) {
            return ResponseEntity.badRequest().body("Game room not found");
        } else {
            return ResponseEntity.ok("Game room deleted successfully");
        }

    }

    // ----------------------------------

    @MessageMapping("/join/{gameRoomID}")
    @SendTo("/topic/message/{gameRoomID}")
    public GameRoom join(@DestinationVariable String gameRoomID, @Payload User user) {
        System.out.println("Joining game room: " + user.getUsername());
        return null;
    }

    @MessageMapping("/message/{gameRoomID}")
    @SendTo("/topic/message/{gameRoomID}")
    public Message sendMessage(@Payload Message message, @DestinationVariable String gameRoomID) {
        return message;
    }

    @MessageMapping("/welcome/{groupId}")
    @SendTo("/topic/welcome/{groupId}")
    public Welcome hello(@DestinationVariable String groupId,@Payload User user) {
        System.out.println(user);
        return new Welcome(groupId, "welcome to the chat ");

    }

    @MessageMapping("/echo")
    @SendTo("/topic/test")
    public String ass() {
        System.out.println("här komm ett meddelande");
        return "3===D";
    }

}
