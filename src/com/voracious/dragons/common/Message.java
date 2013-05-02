package com.voracious.dragons.common;

import java.io.UnsupportedEncodingException;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

public class Message {
	public static final byte START_OF_HEADING = (byte) 1;
	public static final byte START_OF_TEXT = (byte) 2;
	public static final byte END_OF_TRANSMISSION = (byte) 4;

	private byte[] bytes;
	private SocketChannel sender;
	private boolean isDisconnecting;

	public Message(SocketChannel from, byte[] bytes) {
		this.sender = from;
		this.bytes = bytes;
		
		if(bytes == null || bytes.length < 1){
			throw new IllegalArgumentException("message data cannot be empty");
		}
	}

	public byte[] getBytes() {
	    byte[] result = new byte[bytes.length-2];
	    System.arraycopy(bytes, 1, result, 0, result.length);
		return result;
	}
	
	public boolean isString(){
		return bytes[0] == START_OF_TEXT || this.isDisconnecting;
	}

	@Override
	public String toString(){
		String result = "";
		
		if(bytes[0] == START_OF_TEXT) {
			try {
				byte[] outBytes = new byte[bytes.length - 2];
				System.arraycopy(bytes, 1, outBytes, 0, outBytes.length);
				result = new String(outBytes, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				Logger.getLogger(Message.class).error("Unsupported Encoding", e);
			}
		}else if(bytes[0] == START_OF_HEADING) {
			result += "binary: ";
			for(int i=1; i<bytes.length-1; i++){
				result += Integer.toString(bytes[i], 16) + " ";
			}
		}else{
			result = "Invalid format";
		}
		
		return result;
	}

	public static byte[] format(String message) {
		byte[] strbytes = (message).getBytes();
		byte[] mbytes = new byte[2 + strbytes.length]; //*2 because java chars are 2 bytes to support unicode
		mbytes[0] = Message.START_OF_TEXT;
		
		System.arraycopy(strbytes, 0, mbytes, 1, strbytes.length);
		
		mbytes[mbytes.length-1] = Message.END_OF_TRANSMISSION;
		
		return mbytes;
	}

	public static byte[] format(byte[] message) {
		byte[] mbytes = new byte[2 + message.length];
		mbytes[0] = Message.START_OF_HEADING;
		
		System.arraycopy(message, 0, mbytes, 1, message.length);
		
		mbytes[mbytes.length-1] = Message.END_OF_TRANSMISSION;
		
		return mbytes;
	}

	public SocketChannel getSender() {
		return sender;
	}

	public void setDisconnecting(boolean isDisconnecting) {
		this.isDisconnecting = isDisconnecting;
	}

	public boolean isDisconnecting() {
		return this.isDisconnecting;
	}
}
