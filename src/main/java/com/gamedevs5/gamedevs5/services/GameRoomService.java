package com.gamedevs5.gamedevs5.services;

import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;

import org.springframework.stereotype.Service;

import com.gamedevs5.gamedevs5.models.Message;
import com.gamedevs5.gamedevs5.models.User;

import com.gamedevs5.gamedevs5.models.Gameroom.GameRoom;
import com.gamedevs5.gamedevs5.models.Gameroom.GameRoomChat;
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

            newGameRoom.setListOfPlayers(Collections.emptyList());
            newGameRoom.setStatus(false);

            GameRoomChat gameRoomChat = new GameRoomChat();

            gameRoomChat.setListOfMessages(Collections.emptyList());
            newGameRoom.setRoomChat(gameRoomChat);

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

    public GameRoom joinGameRoom(String gameRoomID, User user) {

        GameRoom gameRoom = getGameRoomById(gameRoomID);
        if (gameRoom == null) {
            return null;
        }
        gameRoom.getListOfPlayers().add(user);

        return mongoOperations.save(gameRoom);
    }

    public GameRoom leaveGameRoom(String gameRoomID, User user) {

        GameRoom gameRoom = getGameRoomById(gameRoomID);
        if (gameRoom == null) {
            return null;
        }
        gameRoom.getListOfPlayers().remove(user);
        return mongoOperations.save(gameRoom);
    }

    public GameRoom sendMessageToGroup(String gameRoomID, Message message) {
        GameRoom gameRoom = getGameRoomById(gameRoomID);
        if (gameRoom == null) {
            return null;
        }
        gameRoom.getRoomChat().getListOfMessages().add(message);
        return mongoOperations.save(gameRoom);
    }

}
