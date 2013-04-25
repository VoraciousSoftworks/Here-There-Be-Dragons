package com.voracious.dragons.common;

public class GameInfo {
    private int gameId;
    private String otherPlayer;
    private long lastMoveTime;
    private boolean lastMoveByMe;
    private boolean canMakeTurn;
    
    public GameInfo(int gameId, String otherPlayer, long lastMoveTime, boolean lastMoveByMe, boolean canMakeTurn){
        this.gameId = gameId;
        this.otherPlayer = otherPlayer;
        this.lastMoveTime = lastMoveTime;
        this.lastMoveByMe = lastMoveByMe;
        this.canMakeTurn = canMakeTurn;
    }
    
    public GameInfo(String data){
        String[] parts = data.split(":");
        gameId = Integer.parseInt(parts[0]);
        otherPlayer = parts[1];
        lastMoveTime = Long.parseLong(parts[2]);
        lastMoveByMe = Integer.parseInt(parts[3]) == 1;
        canMakeTurn = Integer.parseInt(parts[4]) == 1;
    }
    
    public String toString(){
        String result = "";
        
        result += gameId + ":";
        result += otherPlayer + ":";
        result += lastMoveTime + ":";
        result += (lastMoveByMe ? 1 : 0) + ":";
        result += (canMakeTurn ? 1 : 0);
        
        return result;
    }
    
    public int getGameId() {
        return gameId;
    }

    public String getOtherPlayer() {
        return otherPlayer;
    }

    public long getLastMoveTime() {
        return lastMoveTime;
    }

    public boolean wasLastMoveByMe() {
        return lastMoveByMe;
    }

    public boolean canMakeTurn() {
        return canMakeTurn;
    }
}
