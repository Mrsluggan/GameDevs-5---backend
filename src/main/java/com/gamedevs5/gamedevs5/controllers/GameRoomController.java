package com.gamedevs5.gamedevs5.controllers;

import java.util.List;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamedevs5.gamedevs5.dto.UserDTO;
import com.gamedevs5.gamedevs5.dto.Welcome;
import com.gamedevs5.gamedevs5.models.Message;
import com.gamedevs5.gamedevs5.models.User;
import com.gamedevs5.gamedevs5.models.Gameroom.Canvas;
import com.gamedevs5.gamedevs5.models.Gameroom.GameRoom;
import com.gamedevs5.gamedevs5.services.GameRoomService;
import com.gamedevs5.gamedevs5.services.UserService;

@RestController()
@RequestMapping("/api/gameroom/")
@CrossOrigin(origins = "http://localhost:5173")
public class GameRoomController {

    private final GameRoomService gameRoomService;
    private final UserService userService;
    private final SimpMessagingTemplate messagingTemplate;


    public GameRoomController(GameRoomService gameRoomService, UserService userService, SimpMessagingTemplate messagingTemplate) {
        this.userService = userService;
        this.gameRoomService = gameRoomService;
        this.messagingTemplate = messagingTemplate;
    }

    // Basic Crudd ----------------------------------
    @PostMapping("/rewardPoints")
    public ResponseEntity<String> rewardPoints(@RequestParam String username) {
        try {
            // Award 2 points to the user with the given username
            userService.addPoints(username, 2);
            return ResponseEntity.ok("Points rewarded");
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unable to reward points: " + e.getMessage());
        }
    }
    @GetMapping("{gameRoomID}")
    public ResponseEntity<GameRoom> getGameRoom(@PathVariable("gameRoomID") String gameRoomID) {
        GameRoom gameRoom = gameRoomService.getGameRoomById(gameRoomID);
        if (gameRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(gameRoom);
    }

    @GetMapping("")
    public ResponseEntity<List<GameRoom>> getGameRooms() {
        return ResponseEntity.ok(gameRoomService.getAllGamerooms());
    }

    @GetMapping("{gameRoomID}/players")
    public ResponseEntity<List<User>> getPlayersInGameRoom(@PathVariable("gameRoomID") String gameRoomID) {
        List<User> players = gameRoomService.getPlayersInGameRoom(gameRoomID);

        if (players == null) {
            players = Collections.emptyList();
        }

        return ResponseEntity.ok(players);
    }

    @PostMapping("create")
    public ResponseEntity<GameRoom> createGameRoom(@RequestBody GameRoom gameRoom) {
        System.out.println("newGameRoom: " + gameRoom.getGameRoomName());
        return ResponseEntity.ok(gameRoomService.createGameRoom(gameRoom));
    }

    @DeleteMapping("delete/{gameRoomID}/{gameRoomOwner}")
    public ResponseEntity<?> deleteGameRoom(@PathVariable("gameRoomID") String gameRoomID, @PathVariable("gameRoomOwner") String gameRoomOwner) {
        if (gameRoomService.deleteGameRoom(gameRoomID, gameRoomOwner) == null) {
            return ResponseEntity.badRequest().body("Game room not found");
        } else {
            return ResponseEntity.ok("Game room deleted successfully");
        }

    }

    @GetMapping("painter/{gameRoomID}")
    public ResponseEntity<String> getPainter(@PathVariable("gameRoomID") String gameRoomID) {
        return ResponseEntity.ok(gameRoomService.getPainter(gameRoomID));
    }

    @GetMapping("randomword/{gameRoomID}")
    public ResponseEntity<String> getRandomWord(@PathVariable("gameRoomID") String gameRoomID) {
        String randomWord = gameRoomService.getRandomWord(gameRoomID);
        if (randomWord == null) {
            System.out.println("No word found for gameroom:" + gameRoomID);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No word found");
        }
        System.out.println("Random word: " + randomWord);
        return ResponseEntity.ok(randomWord);
    }

    @PostMapping("setpainter/{gameRoomID}")
    public ResponseEntity<GameRoom> setPainter(@PathVariable("gameRoomID") String gameRoomID) {
        return ResponseEntity.ok(gameRoomService.setPainter(gameRoomID));
    }

    // ----------------------------------

    @GetMapping("checkplayer/{username}")
    public ResponseEntity<GameRoom> getUser(@PathVariable("username") String username) {
        GameRoom gameRoom = gameRoomService.checkIfUserIsInGame(username);
        if (gameRoom == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(gameRoom);
    }

    // -------------------------------
    @PutMapping("/join/{gameRoomID}")
    public void join(@PathVariable String gameRoomID, @RequestBody User user) {
        System.out.println("Joining game room: " + user.getUsername());
        gameRoomService.joinGameRoom(gameRoomID, user);

    }

    @PutMapping("/leave/{gameRoomID}")
    public void leaveGameRoom(@PathVariable String gameRoomID, @RequestBody User user) {
        System.out.println("Leaving game room: " + user.getUsername());
        gameRoomService.leaveGameRoom(gameRoomID, user);
    }

    @MessageMapping("/message/{gameRoomID}")
    @SendTo("/topic/message/{gameRoomID}")
    public Message sendMessage(@DestinationVariable String gameRoomID, @RequestBody Message message) {
        gameRoomService.sendMessageToGroup(gameRoomID, message);
        return message;
    }

    @MessageMapping("/welcome/{groupId}")
    @SendTo("/topic/welcome/{groupId}")
    public Message hello(@DestinationVariable String groupId, @RequestBody UserDTO user) {
        return new Message(user.getUsername(), "Welcome to the game " + user.getUsername());
    }

    @MessageMapping("/updatecanvase/{groupId}")
    @SendTo("/topic/updatecanvas/{groupId}")
    public Canvas updateCanvas(@DestinationVariable String groupId, @RequestBody Canvas canvas) {
        System.out.println(canvas.getX());
        return canvas;
    }

    @MessageMapping("/clearcanvas/{gameRoomID}")
    public void handleClearCanvas(@DestinationVariable String gameRoomID) {
        messagingTemplate.convertAndSend("/topic/clearcanvas/" + gameRoomID, "");
    }
}
