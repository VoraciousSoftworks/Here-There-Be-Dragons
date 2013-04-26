package com.voracious.dragons.client.net;

import java.util.ArrayList;
import java.util.List;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.screens.GameListScreen;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.GameInfo;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;

public class GameListPacket implements Packet {

    @Override
    public boolean wasCalled(Message message) {
        String msg = message.toString();
        return msg.startsWith("GLE:") || msg.startsWith("GLS:");
    }

    @Override
    public void process(Message message, ConnectionManager cm) {
        String msg = message.toString();
        if(msg.startsWith("GLE:")){
            
        }else if(msg.startsWith("GLS:")){
            msg = msg.substring(4);
            int numGames = Integer.parseInt(msg.substring(0, msg.indexOf(":")));
            msg = msg.substring(msg.indexOf(":") + 1);
            
            List<GameInfo> games = new ArrayList<>(numGames);
            String[] gamestrs = msg.split(";");
            for(int i = 1; i < gamestrs.length; i++){
                games.add(new GameInfo(gamestrs[i]));
            }
            
            ((GameListScreen) Game.getScreen(GameListScreen.ID)).onListRecieved(games);
        }
    }

    @Override
    public boolean isString() {
        return true;
    }

}
