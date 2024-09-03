package com.gamedevs5.gamedevs5.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.gamedevs5.gamedevs5.models.Gameroom.GameRoom;

@Service
public class GameRoomService {

    private final MongoOperations mongoOperations;

    public GameRoomService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Optional<GameRoom> getGameRoomById(String gameRoomID) {
        if (gameRoomID == null || gameRoomID.trim().isEmpty()) {
            throw new IllegalArgumentException("GameRoom ID cannot be null or empty");
        }
        try {
            GameRoom gameRoom = mongoOperations.findById(gameRoomID, GameRoom.class);
            return Optional.ofNullable(gameRoom);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the game room with ID: " + gameRoomID, e);
        }
    }

    public List<GameRoom> getAllGamerooms() {
        try {
            List<GameRoom> listOfGameRooms = mongoOperations.findAll(GameRoom.class);
            if (listOfGameRooms == null) {
                throw new Exception("There are no active rooms found");
            }
            return listOfGameRooms;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the game rooms", e);
        }

    }

    public GameRoom createGameRoom(GameRoom newGameRoom) {
        try {
            if (newGameRoom == null) {
                throw new Exception("The provided GameRoom object is null.");
            }

            return mongoOperations.save(newGameRoom);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the game room", e);
        }
    }

    public void deleteGameRoom(String gameRoomID) {
        if (gameRoomID == null || gameRoomID.trim().isEmpty()) {
            throw new IllegalArgumentException("GameRoom ID cannot be null or empty");
        }
        try {
            mongoOperations.remove(getGameRoomById(gameRoomID));

        } catch (Exception e) {
            throw new RuntimeException("An error occurred while removing the game room", e);
        }

    }

}
