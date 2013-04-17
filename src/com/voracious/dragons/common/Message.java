package com.voracious.dragons.common;

import java.nio.channels.SocketChannel;

public class Message {
	private byte[] bytes;
	private SocketChannel sender;
	private boolean isDisconnecting;
	
	public Message(SocketChannel from, byte[] bytes){
		this.sender = from;
		this.bytes = bytes;
	}
	
	public byte[] getBytes(){
		return bytes;
	}
	
	@Override
	public String toString(){
		return null;
	}
	
	public SocketChannel getSender(){
		return sender;
	}
	
	public void setDisconnecting(boolean isDisconnecting){
		this.isDisconnecting = isDisconnecting;
	}
	
	public boolean isDisconnecting(){
		return this.isDisconnecting;
	}
}
