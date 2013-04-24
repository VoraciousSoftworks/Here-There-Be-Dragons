package com.voracious.dragons.client.net;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.screens.StatScreen;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;

public class StatisticsPacket implements Packet {

    @Override
    public boolean wasCalled(Message message) {
        return message.toString().startsWith("PS:");
    }

    @Override
    public void process(Message message, ConnectionManager cm) {
        String msg = message.toString().substring(3);
        char type = msg.charAt(0);
        String data = msg.substring(2);
        
        ((StatScreen) Game.getScreen(StatScreen.ID)).onStatRecieved(type, data);
    }

    @Override
    public boolean isString() {
        return true;
    }
}
