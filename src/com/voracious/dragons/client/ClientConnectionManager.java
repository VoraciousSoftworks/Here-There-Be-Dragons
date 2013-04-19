package com.voracious.dragons.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.voracious.dragons.common.ConnectionManager;

public class ClientConnectionManager {
	private Logger logger = Logger.getLogger(ClientConnectionManager.class);
	private String hostname;
	private int port;
	private SocketChannel server;
	private ConnectionManager cm;
	private String sessionId;
	
	public ClientConnectionManager(String hostname, int port){
		this.hostname = hostname;
		this.port = port;
		try {
			cm = new ConnectionManager();
			server = SocketChannel.open(new InetSocketAddress(InetAddress.getByName(hostname), port));
			server.configureBlocking(false);
			server.register(cm.getReadSelector(), SelectionKey.OP_READ, new ByteArrayOutputStream());
			new Thread(cm).start();
			new Thread(new ClientMessageProcessor(this, cm.getMessageQueue())).start();
		} catch (UnknownHostException e) {
			logger.error("Unknown host", e);
		} catch (IOException e) {
			logger.error("Could not open connection", e);
		}
	}
	
	public void disconnect() {
		try {
			server.close();
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	public void setSessionId(String id) {
		this.sessionId = id;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
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
