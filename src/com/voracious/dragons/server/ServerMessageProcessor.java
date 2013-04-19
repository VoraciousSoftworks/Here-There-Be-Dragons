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
				
				register(msg.substring(0, splitLoc), msg.substring(splitLoc + 1, msg.length()));
			}
		}else if(message.isDisconnecting()){
			
		}
	}
	
	public void authenticate(SocketChannel sender, String username, String password) {
		String storedHash = Main.getDB().getPasswordHash(username);
		if(Crypto.check(password, storedHash)){
			try {
				User user = scm.getUser(sender.getRemoteAddress().toString());
				if(user != null){
					user.setAuthenticated(true);
					scm.sendMessage(user, "LS:" + Crypto.getSessionId());
				}else{
					scm.sendMessage(user, "E:User not found");
				}
			} catch (IOException e) {
				logger.error("Could authenticate", e);
				scm.sendMessage(sender, "E:Server Error");
			}
		}else{
			scm.sendMessage(sender, "E:Invalid Password");
		}
	}
	
	public void register(String username, String password) {
		
	}
}
