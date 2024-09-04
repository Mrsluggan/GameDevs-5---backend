package com.gamedevs5.gamedevs5.models.Gameroom;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.gamedevs5.gamedevs5.models.Message;

public class GameRoomChat {

    private List<Message> listOfMessages = List.of();

    public GameRoomChat() {
    }

    public GameRoomChat(List<Message> listOfMessages) {

        this.listOfMessages = listOfMessages;
    }

    public List<Message> getListOfMessages() {
        return listOfMessages;
    }

    public void setListOfMessages(List<Message> listOfMessages) {
        this.listOfMessages = listOfMessages;
    }

}
