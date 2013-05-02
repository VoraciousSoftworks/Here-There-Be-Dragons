package com.voracious.dragons.server.net;

import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.common.Turn;
import com.voracious.dragons.server.Main;
import com.voracious.dragons.server.User;

public class ServerTurnPacket implements Packet{

	@Override
	public boolean wasCalled(Message message) {
		return message.getBytes()[0] == 7;
	}

	@Override
	public void process(Message message, ConnectionManager cm) {
		ServerConnectionManager scm = (ServerConnectionManager) cm;
		byte[] mbytes = message.getBytes();
		byte[] turn = new byte[mbytes.length-1];
		System.arraycopy(mbytes, 1, turn, 0, turn.length);
		Turn newTurn = new Turn(turn);
		String PID = scm.getUserByID(newTurn.getSessionId()).getUsername();
		if(PID == null)
		    return;
		
		boolean shouldSimulate = Main.getDB().insertTurn(newTurn.getGameId(), PID, newTurn.toString());
		
		if(shouldSimulate){
		    String otherPid = Main.getDB().getOtherPid(PID, newTurn.getGameId());
		    User other = scm.getUserByName(otherPid);
    		if(other != null && other.isPlaying() && other.getCurrentGame() == newTurn.getGameId()){
    		    newTurn.setSessionId("//////////////////////////////////////////8=");
    		    byte[] safeTurn = newTurn.toBytes().array();
    		    byte[] toSend = new byte[safeTurn.length + 1];
    		    toSend[0] = 7;
    		    System.arraycopy(safeTurn, 0, toSend, 1, safeTurn.length);
    		    scm.sendMessage(other, toSend);
    		}
    		
    		Turn othersTurn = Main.getDB().getLatestTurn(newTurn.getGameId(), otherPid);
    		if(othersTurn != null){
        		othersTurn.setSessionId("//////////////////////////////////////////8=");
        		byte[] safeTurn = othersTurn.toBytes().array();
                byte[] toSend = new byte[safeTurn.length + 1];
                toSend[0] = 7;
                System.arraycopy(safeTurn, 0, toSend, 1, safeTurn.length);
        		scm.sendMessage(message.getSender(), toSend);
    		}
		}
	}

	@Override
	public boolean isString() {
		return false;
	}
}
