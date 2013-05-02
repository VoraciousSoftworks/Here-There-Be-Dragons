package com.voracious.dragons.server;

import java.nio.channels.SocketChannel;

public class User {
	private SocketChannel connection;
	
	private boolean isAuthenticated;
	private boolean isPlaying;
	private int currentGame;
	private boolean isSpectating;
	
	private String username;
	private String sessionId;
	
	public User(SocketChannel connection){
		this.connection = connection;
		isAuthenticated = false;
		isPlaying = false;
		isSpectating = false;
		currentGame = 0;
	}

	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
	public int getCurrentGame() {
	    return currentGame;
	}
	
	public void setCurrentGame(int gid) {
	    this.currentGame = gid;
	}

	public boolean isSpectating() {
		return isSpectating;
	}

	public void setSpectating(boolean isSpectating) {
		this.isSpectating = isSpectating;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public SocketChannel getConnection() {
		return connection;
	}
}
