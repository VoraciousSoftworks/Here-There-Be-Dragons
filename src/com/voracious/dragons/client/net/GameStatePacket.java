package com.voracious.dragons.client.net;
import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.screens.PlayScreen;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;


public class GameStatePacket implements Packet {

    @Override
    public boolean wasCalled(Message message) {
        return message.toString().startsWith("GS:");
    }

    @Override
    public void process(Message message, ConnectionManager cm) {
        String gs = message.toString().substring(3);
        ((PlayScreen) Game.getScreen(PlayScreen.ID)).onGameStateReceived(gs);
    }

    @Override
    public boolean isString() {
        return true;
    }
}
