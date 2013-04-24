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

public class ServerConnectionManager extends ConnectionManager {
	public static final String default_hostname = "0.0.0.0";
	public static final int default_port = 35580;
	public static final int CHANNEL_CONNECT_SLEEP = 300;
	
	private Logger logger = Logger.getLogger(ServerConnectionManager.class);
	
	private ServerSocketChannel ssc;
	private Map<String, User> users;
	
	public ServerConnectionManager(){
	    super();
		users = new HashMap<>();
		bindAddresses(default_hostname, default_port);

		new Thread(new Runnable(){
            @Override
            public void run() {
                myRun();
            }
		}).start();
		
		new Thread(new ServerMessageProcessor(this, getMessageQueue())).start();
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

				sendMessage(clientChannel, "Hello!");
				
				clientChannel.configureBlocking(false);
				clientChannel.register(getReadSelector(), SelectionKey.OP_READ, new ByteArrayOutputStream());
				
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
	
	public User getUserByID(String sessionId) {
	    for(User u : users.values()){
	        if(u.getSessionId() == sessionId){
	            return u;
	        }
	    }
	    
	    return null;
	}
	
	public User getUserByIP(String remoteAddress){
		return users.get(remoteAddress);
	}
	
	public void sendMessage(User user, String message){
		sendMessage(user.getConnection(), message);
	}
	
	public void sendMessage(User user, byte[] message){
		sendMessage(user.getConnection(), message);
	}

	public void myRun() {
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
