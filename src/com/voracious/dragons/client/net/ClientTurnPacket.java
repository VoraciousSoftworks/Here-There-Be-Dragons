package com.voracious.dragons.client.net;
import com.voracious.dragons.client.screens.PlayScreen;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.common.Turn;

public class ClientTurnPacket implements Packet{

	@Override
	public boolean wasCalled(Message message) {
		String msg = message.toString();
		return msg.getBytes()[0]==7;
	}

	@Override
	public void process(Message message, ConnectionManager cm) {
		ClientConnectionManager ccm = (ClientConnectionManager) cm;
        Turn neuTurn = new Turn(message.getBytes());
        PlayScreen.onTurnCalled(neuTurn);
	}

	@Override
	public boolean isString() {
		// TODO Auto-generated method stub
		return false;
	}

}