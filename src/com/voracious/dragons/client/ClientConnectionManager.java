package com.voracious.dragons.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.voracious.dragons.common.ConnectionManager;

public class ClientConnectionManager {
	private Logger logger = Logger.getLogger(ClientConnectionManager.class);
	private SocketChannel server;
	private ConnectionManager cm;
	
	public ClientConnectionManager(String hostname, int port){
		try {
			cm = new ConnectionManager();
			server = SocketChannel.open(new InetSocketAddress(InetAddress.getByName(hostname), port));
			
			new Thread(cm).start();
		} catch (UnknownHostException e) {
			logger.error("Unknown host", e);
		} catch (IOException e) {
			logger.error("Could not open connection", e);
		}
	}
	
	public void sendMessage(String message){
		cm.sendMessage(server, message);
	}
	
	public void sendMessage(byte[] message){
		cm.sendMessage(server, message);
	}
	
	public void close(){
		sendMessage("quit");
		try {
			server.close();
		} catch (IOException e) {}
	}
}
