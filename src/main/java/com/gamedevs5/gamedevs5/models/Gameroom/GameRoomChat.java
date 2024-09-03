package com.gamedevs5.gamedevs5.models.Gameroom;

import java.util.List;

import com.gamedevs5.gamedevs5.models.Message;

public class GameRoomChat {
    private String id;
    private String gameRoomId;
    private List<Message> listOfMessages;

    public GameRoomChat() {
    }

    public GameRoomChat(String id, String gameRoomId, List<Message> listOfMessages) {
        this.id = id;
        this.gameRoomId = gameRoomId;
        this.listOfMessages = listOfMessages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameRoomId() {
        return gameRoomId;
    }

    public void setGameRoomId(String gameRoomId) {
        this.gameRoomId = gameRoomId;
    }

    public List<Message> getListOfMessages() {
        return listOfMessages;
    }

    public void setListOfMessages(List<Message> listOfMessages) {
        this.listOfMessages = listOfMessages;
    }

}
