package com.voracious.dragons.client.net;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.screens.PlayScreen;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;

public class ClientTurnPacket implements Packet{

	@Override
	public boolean wasCalled(Message message) {
		return message.getBytes()[0]==7;
	}

	@Override
	public void process(Message message, ConnectionManager cm) {
	    byte[] msg = message.getBytes();
	    byte[] turndata = new byte[msg.length-1];
	    System.arraycopy(msg, 1, turndata, 0, turndata.length);
        ((PlayScreen) Game.getScreen(PlayScreen.ID)).onTurnReceived(turndata);
	}

	@Override
	public boolean isString() {
		return false;
	}
}
