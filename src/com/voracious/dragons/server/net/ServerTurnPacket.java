package com.voracious.dragons.server.net;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.common.Turn;
import com.voracious.dragons.server.Main;

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
		Main.getDB().insertTurn(newTurn.getGameId(), PID, newTurn.toString());
	}

	@Override
	public boolean isString() {
		return false;
	}

}
