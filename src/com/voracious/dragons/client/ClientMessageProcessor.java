package com.voracious.dragons.client;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.voracious.dragons.client.screens.LoginScreen;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.MessageProcessor;

public class ClientMessageProcessor extends MessageProcessor {

	private ClientConnectionManager ccm;
	private static Logger logger = Logger.getLogger(ClientMessageProcessor.class);
	
	public ClientMessageProcessor(ClientConnectionManager ccm, BlockingQueue<Message> messageQueue) {
		super(messageQueue);
		this.ccm = ccm;
	}

	@Override
	public void processMessage(Message message) {
		//TODO: store toString
		if(message.isString()) {
			if(message.toString().equals("Hello!")){
				String m = "";
				
				if(LoginScreen.isRegistering()){
					m += "R:";
				}else{
					m += "L:";
				}
				
				m += LoginScreen.getUsername() + ":" + LoginScreen.getPassword();
				
				ccm.sendMessage(m);
			}else if(message.toString().startsWith("RS:") || message.toString().startsWith("LS:")){
				if(!LoginScreen.hasLoggedIn()){
					ccm.setSessionId(message.toString().substring(2));
					LoginScreen.onSuccess();
				}
			}else if(message.toString().startsWith("E:")){
				if(!LoginScreen.hasLoggedIn()){
					LoginScreen.onFailure(message.toString().substring(2));
				}
			}
		}
	}
}
