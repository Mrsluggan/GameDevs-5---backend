package com.gamedevs5.gamedevs5.models.Gameroom;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gamedevs5.gamedevs5.models.User;

@Document("gameRoom")
public class GameRoom {

    @Id
    private String id;
    private String gameRoomName;
    private User roomOwner;

    private List<GameRoomChat> chatMessages;
    private List<User> listOfPlayers;
    private List<User> leaderboard;
    private Boolean status;

    public GameRoom() {

    }

    public GameRoom(String id, String gameRoomName, User roomOwner, List<GameRoomChat> chatMessages,List<User> listOfPlayers, List<User> leaderboard, Boolean status) {
        this.id = id;
        this.gameRoomName = gameRoomName;
        this.roomOwner = roomOwner;
        this.chatMessages = chatMessages;
        this.listOfPlayers = listOfPlayers;
        this.leaderboard = leaderboard;
        this.status = status;
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

    public User getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(User roomOwner) {
        this.roomOwner = roomOwner;
    }

    public List<GameRoomChat> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<GameRoomChat> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public List<User> getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(List<User> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public List<User> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(List<User> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}
