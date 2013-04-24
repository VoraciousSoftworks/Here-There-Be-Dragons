package com.voracious.dragons.common;

public class GameInfo {
    private int gameId;
    private String gameName;
    private String otherPlayer;
    private long lastMoveTime;
    private boolean lastMoveByMe;
    
    public GameInfo(int gameId, String gameName, String otherPlayer, long lastMoveTime, boolean lastMoveByMe){
        this.gameId = gameId;
        this.gameName = gameName;
        this.otherPlayer = otherPlayer;
        this.lastMoveTime = lastMoveTime;
        this.lastMoveByMe = lastMoveByMe;
    }
    
    public int getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getOtherPlayer() {
        return otherPlayer;
    }

    public long getLastMoveTime() {
        return lastMoveTime;
    }

    public boolean isLastMoveByMe() {
        return lastMoveByMe;
    }
}
