package com.voracious.dragons.client.net;

import org.apache.log4j.Logger;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.screens.GameListScreen;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;

public class ClientPlayGamePacket implements Packet {
    
    private static Logger logger = Logger.getLogger(ClientPlayGamePacket.class);
    
    @Override
    public boolean wasCalled(Message message) {
        String msg = message.toString();
        return msg.startsWith("PGS:") || msg.startsWith("PGE:");
    }

    @Override
    public void process(Message message, ConnectionManager cm) {
        String msg = message.toString();
        if(msg.startsWith("PGS:")){
            ((GameListScreen)Game.getScreen(GameListScreen.ID)).onGameStateReceived(msg.substring(4));
        }else if(msg.startsWith("PGE:")){
            logger.error("Error playing game: " + msg.substring(4));
        }
    }

    @Override
    public boolean isString() {
        return true;
    }
}
