package com.voracious.dragons.server.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.server.User;

public class ServerConnectionManager implements Runnable {
	public static final String default_hostname = "0.0.0.0";
	public static final int default_port = 35580;
	public static final int CHANNEL_CONNECT_SLEEP = 300;
	
	private Logger logger = Logger.getLogger(ServerConnectionManager.class);
	
	private ConnectionManager cm;
	private ServerSocketChannel ssc;
	private Map<String, User> users;
	
	public ServerConnectionManager(){
		users = new HashMap<String, User>();
		bindAddresses(default_hostname, default_port);
		cm = new ConnectionManager();
		new Thread(cm).start();
		new Thread(new ServerMessageProcessor(this, cm.getMessageQueue())).start();
	}
	
	public void bindAddresses(String hostname, int port){
		try {
			ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ssc.bind(new InetSocketAddress(InetAddress.getByName(hostname), port));
		} catch (IOException e) {
			logger.error("Failed to bind ports", e);
		}
	}
	
	public void acceptNewConnections(){
		SocketChannel clientChannel;
		try {
			while((clientChannel = ssc.accept()) != null){
				clientChannel.configureBlocking(false);
				logger.info("Client connected: " + clientChannel.getRemoteAddress().toString());

				cm.sendMessage(clientChannel, "Hello!");
				
				clientChannel.configureBlocking(false);
				clientChannel.register(cm.getReadSelector(), SelectionKey.OP_READ, new ByteArrayOutputStream());
				
				User newUser = new User(clientChannel);
				users.put(clientChannel.getRemoteAddress().toString(), newUser);
			}
		} catch (IOException e) {
			logger.error("Could not accept connection", e);
		}
	}
	
	public void addUser(User user){
		try {
			users.put(user.getConnection().getRemoteAddress().toString(), user);
		} catch (IOException e) {
			logger.error("Could not get user's remote address", e);
		}
	}
	
	public void disconnectUser(String userRemAdd){
		try {
			users.get(userRemAdd).getConnection().close();
			users.remove(userRemAdd);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	public User getUser(String remoteAddress){
		return users.get(remoteAddress);
	}
	
	public void sendMessage(User user, String message){
		cm.sendMessage(user.getConnection(), message);
	}
	
	public void sendMessage(User user, byte[] message){
		cm.sendMessage(user.getConnection(), message);
	}
	
	public void sendMessage(SocketChannel chan, String message){
		cm.sendMessage(chan, message);
	}
	
	public void sendMessage(SocketChannel chan, byte[] message){
		cm.sendMessage(chan, message);
	}

	@Override
	public void run() {
		while(true){
			acceptNewConnections();
			try {
				Thread.sleep(CHANNEL_CONNECT_SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
