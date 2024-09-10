package com.gamedevs5.gamedevs5.models.Gameroom;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gamedevs5.gamedevs5.models.User;
import com.gamedevs5.gamedevs5.models.Word;

@Document("gameRoom")
public class GameRoom {

    @Id
    private String id;
    private String gameRoomName;
    private String roomOwner;
    private String painter; 
    private String randomWord;
    private GameRoomChat gameRoomChat;
    private List<User> listOfPlayers;
    private Boolean status;


    public GameRoom() {

    }


    public GameRoom(String id, String gameRoomName, String roomOwner, String painter, String randomWord,
            GameRoomChat gameRoomChat, List<User> listOfPlayers, Boolean status) {
        this.id = id;
        this.gameRoomName = gameRoomName;
        this.roomOwner = roomOwner;
        this.painter = painter;
        this.randomWord = randomWord;
        this.gameRoomChat = gameRoomChat;
        this.listOfPlayers = listOfPlayers;
        this.status = status;
    }

    public String getPainter() {
        return painter;
    }

    public void setPainter(String painter) {
        this.painter = painter;
    }

    public String getRandomWord() {
        return randomWord;
    }

    public void setRandomWord(String word) {
        this.randomWord = word;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameRoomName() {
        return gameRoomName;
    }

    public void setGameRoomName(String gameRoomName) {
        this.gameRoomName = gameRoomName;
    }

    public String getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(String roomOwner) {
        this.roomOwner = roomOwner;
    }

    public GameRoomChat getRoomChat() {
        return gameRoomChat;
    }

    public void setRoomChat(GameRoomChat gameRoomChat) {
        this.gameRoomChat = gameRoomChat;
    }

    public List<User> getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(List<User> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
