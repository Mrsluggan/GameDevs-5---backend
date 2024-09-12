package com.gamedevs5.gamedevs5.models.Gameroom;

public class Canvas {

    private int x;
    private int y;
    private String color;
    private String currentPlayer;

    public Canvas(int x, int y, String color, String currentPlayer) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.currentPlayer = currentPlayer;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

}
