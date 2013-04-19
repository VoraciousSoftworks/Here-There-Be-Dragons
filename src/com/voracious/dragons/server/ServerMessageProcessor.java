package com.voracious.dragons.server;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.MessageProcessor;

public class ServerMessageProcessor extends MessageProcessor {

	private ServerConnectionManager scm;
	private static Logger logger = Logger.getLogger(ServerMessageProcessor.class);
	
	//TODO: Input validation
	
	public ServerMessageProcessor(ServerConnectionManager scm, BlockingQueue<Message> messageQueue) {
		super(messageQueue);
		this.scm = scm;
	}

	@Override
	public void processMessage(Message message) {
		if(message.isString()){
			String msg = message.toString();
			if(msg.startsWith("L:")){
				msg = msg.substring(2);
				int splitLoc = msg.indexOf(":");
				
				authenticate(message.getSender(), msg.substring(0, splitLoc), msg.substring(splitLoc + 1, msg.length()));
			}else if(msg.startsWith("R:")){
				msg = msg.substring(2);
				int splitLoc = msg.indexOf(":");
				
				register(message.getSender(), msg.substring(0, splitLoc), msg.substring(splitLoc + 1, msg.length()));
			}
		}else if(message.isDisconnecting()){
			try {
				scm.disconnectUser(message.getSender().getRemoteAddress().toString());
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	
	public void authenticate(SocketChannel sender, String username, String password) {
		String storedHash = Main.getDB().getPasswordHash(username);
		if(storedHash != null){
			if(Crypto.check(password, storedHash)){
				try {
					User user = scm.getUser(sender.getRemoteAddress().toString());
					if(user != null){
						String session = Crypto.getSessionId();
						user.setSessionId(session);
						user.setUsername(username);
						user.setAuthenticated(true);
						scm.sendMessage(user, "LS:" + session);
					}else{
						scm.sendMessage(user, "E:Connection error");
					}
				} catch (IOException e) {
					logger.error("Couldn't authenticate", e);
					scm.sendMessage(sender, "E:Server error");
				}
			}else{
				scm.sendMessage(sender, "E:Invalid password");
			}
		}else{
			scm.sendMessage(sender, "E:Invalid username");
		}
	}
	
	public void register(SocketChannel sender, String username, String password) {
		if(Main.getDB().getPasswordHash(username) != null){
			Main.getDB().registerUser(username, Crypto.getSaltedHash(password));
			try {
				User user = scm.getUser(sender.getRemoteAddress().toString());
				String session = Crypto.getSessionId(); 
				user.setUsername(username);
				user.setSessionId(session);
				user.setAuthenticated(true);
				scm.sendMessage(user, "RS:" + session);
			} catch (IOException e) {
				logger.error("Couldn't authenticate", e);
				scm.sendMessage(sender, "E:Server error");
			}
		}else{
			scm.sendMessage(sender, "E:Username already registered");
		}
	}
}
