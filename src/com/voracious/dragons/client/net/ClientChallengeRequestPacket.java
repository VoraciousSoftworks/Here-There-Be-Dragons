package com.voracious.dragons.client.net;

import org.apache.log4j.Logger;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.screens.PlayScreen;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.GameState;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;

public class ClientChallengeRequestPacket implements Packet {

    Logger logger = Logger.getLogger(ClientChallengeRequestPacket.class);
    
	@Override
	public boolean wasCalled(Message message) {
		String msg = message.toString();
		return (msg.startsWith("CRS:") || msg.startsWith("CRE:"));
	}

	@Override
	public void process(Message message, ConnectionManager cm) {
		ClientConnectionManager ccm = (ClientConnectionManager) cm;
		String msg = message.toString();
		if(msg.startsWith("CRS:")){
			String [] msgArr = msg.split(":");
			
			((PlayScreen) Game.getScreen(PlayScreen.ID)).init(Integer.parseInt(msgArr[1]), true, true, new GameState(msgArr[2]));
			Game.setCurrentScreen(PlayScreen.ID);
			logger.info("Started new game");
		} else if(msg.startsWith("CRE:")){
			ccm.sendMessage(msg.substring(msg.indexOf(":") + 1, msg.length()));
		} else {
			logger.error("An unknown error occured.");
		}
		
	}

	@Override
	public boolean isString() {
		return true;
	}
}
