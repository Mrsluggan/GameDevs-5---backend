package com.gamedevs5.gamedevs5.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.gamedevs5.gamedevs5.models.Gameroom.GameRoom;
import com.mongodb.client.result.DeleteResult;

@Service
public class GameRoomService {

    private final MongoOperations mongoOperations;

    public GameRoomService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public GameRoom getGameRoomById(String gameRoomID) {

        try {
            GameRoom gameRoom = mongoOperations.findById(gameRoomID, GameRoom.class);
            return gameRoom;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the game room with ID: " + gameRoomID, e);
        }
    }

    public List<GameRoom> getAllGamerooms() {
        try {
            List<GameRoom> listOfGameRooms = mongoOperations.findAll(GameRoom.class);
            if (listOfGameRooms == null || listOfGameRooms.isEmpty()) {
                listOfGameRooms = Collections.emptyList();
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

    public DeleteResult deleteGameRoom(String gameRoomID) {
        GameRoom gameRoom = getGameRoomById(gameRoomID);
        if (gameRoom == null) {
            return null;
        }
        return mongoOperations.remove(gameRoom);

    }

}
