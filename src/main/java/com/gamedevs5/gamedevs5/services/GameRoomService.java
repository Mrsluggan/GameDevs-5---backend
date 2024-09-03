package com.gamedevs5.gamedevs5.services;

import java.util.List;

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

    public GameRoom getGameRoomById(String gameRoomId) {

        return null;
    }

    public List<GameRoom> getAllGamerooms() {

        return null;
    }

    public GameRoom createGameRoom(GameRoom newGameRoom) {

        return null;
    }

}
