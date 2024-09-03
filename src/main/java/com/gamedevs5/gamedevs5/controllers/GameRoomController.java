package com.gamedevs5.gamedevs5.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<GameRoom> createGameRoom(GameRoom gameRoom) {
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


    





}
