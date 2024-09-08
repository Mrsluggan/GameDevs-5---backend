package com.gamedevs5.gamedevs5.services;

import java.util.Collections;
import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamedevs5.gamedevs5.models.Message;
import com.gamedevs5.gamedevs5.models.User;

import com.gamedevs5.gamedevs5.models.Gameroom.GameRoom;
import com.gamedevs5.gamedevs5.models.Gameroom.GameRoomChat;
import com.mongodb.client.result.DeleteResult;

@Service
public class GameRoomService {

    private final MongoOperations mongoOperations;
    private UserService userService;

    public GameRoomService(MongoOperations mongoOperations, UserService userService) {
        this.mongoOperations = mongoOperations;
        this.userService = userService;

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
        for (GameRoom gameRooms : getAllGamerooms()) {

            for (User player : gameRooms.getListOfPlayers()) {
                if (user.getUsername().equals(player.getUsername())) {
                    System.out.println("Denna spelare är redan i ett pågående spel");
                    return null;
                }
            }

        }
        System.out.println("spelare joinade");
        gameRoom.getListOfPlayers().add(user);
        return mongoOperations.save(gameRoom);
    }

    public GameRoom leaveGameRoom(String gameRoomID, User user) {

        GameRoom gameRoom = getGameRoomById(gameRoomID);
        if (gameRoom == null) {
            return null;
        }
        List<User> players = gameRoom.getListOfPlayers();
        players.removeIf(player -> player.getUsername().equals(user.getUsername()));
        return mongoOperations.save(gameRoom);
    }

    public GameRoom startGame(String gameRoomID) {

        GameRoom gameRoom = getGameRoomById(gameRoomID);
        if (gameRoom == null) {
            return null;
        }

        gameRoom.setStatus(true);
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

    public GameRoom checkIfUserIsInGame(String username) {

        for (GameRoom gameRoom : getAllGamerooms()) {
            for (User player : gameRoom.getListOfPlayers()) {
                if (player.getUsername().equals(username)) {
                    return gameRoom;
                }
            }
        }
        return null;
    }

}
