package com.voracious.dragons.server.net;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.server.Main;

public class ServerChallengePacket implements Packet{

	@Override
	public boolean wasCalled(Message message) {
		String msg = message.toString();
		return (msg.startsWith("CR:"));
	}

	@Override
	public void process(Message message, ConnectionManager cm) {
		ServerConnectionManager scm = (ServerConnectionManager) cm;
        String msg = message.toString().substring(2);
        int splitLoc = msg.indexOf(":");
        String sessionId = msg.substring(0, splitLoc);
        String playerToChallenge = msg.substring(splitLoc + 1, msg.length());
        
        if(Main.getDB().getPasswordHash(playerToChallenge) != null){
        	String PID = scm.getUserByID(sessionId).getUsername();
        	int GID = Main.getDB().insertGame(PID,playerToChallenge);
        	int boo;
        	if(Main.getDB().isPlayer1(PID, GID))
        		boo = 1;
        	else
        		boo = 0;
        	scm.sendMessage(message.getSender(), "CRS:"+ GID + ":" + boo);
        	ServerMessageProcessor.logger.debug("Challenge correctly issued");
        }
        
        else
        {
        	scm.sendMessage(message.getSender(), "CRE: User not found");
        	ServerMessageProcessor.logger.debug("Challenge aborted no user.");
        }
        
	}

	@Override
	public boolean isString() {
		return true;
	}

}
