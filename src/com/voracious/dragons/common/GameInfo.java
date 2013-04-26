package com.voracious.dragons.common;

public class GameInfo {
    private int gameId;
    private String otherPlayer;
    private long lastMoveTime;
    private boolean lastMoveByMe;
    private boolean canMakeTurn;
    private boolean isPlayer1;
    
    public GameInfo(int gameId, String otherPlayer, long lastMoveTime, boolean lastMoveByMe, boolean canMakeTurn, boolean isPlayer1){
        this.gameId = gameId;
        this.otherPlayer = otherPlayer;
        this.lastMoveTime = lastMoveTime;
        this.lastMoveByMe = lastMoveByMe;
        this.canMakeTurn = canMakeTurn;
        this.isPlayer1 = isPlayer1;
    }
    
    public GameInfo(String data){
        String[] parts = data.split(":");
        gameId = Integer.parseInt(parts[0]);
        otherPlayer = parts[1];
        lastMoveTime = Long.parseLong(parts[2]);
        lastMoveByMe = Integer.parseInt(parts[3]) == 1;
        canMakeTurn = Integer.parseInt(parts[4]) == 1;
        isPlayer1 = Integer.parseInt(parts[5]) == 1;
    }
    
    public String toString(){
        String result = "";
        
        result += gameId + ":";
        result += otherPlayer + ":";
        result += lastMoveTime + ":";
        result += (lastMoveByMe ? 1 : 0) + ":";
        result += (canMakeTurn ? 1 : 0) + ":";
        result += (isPlayer1 ? 1 : 0);
        
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
    
    public boolean isPlayer1() {
        return isPlayer1;
    }
}
