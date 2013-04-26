package com.voracious.dragons.server.net;

import java.util.List;

import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.GameInfo;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.server.DBHandler;
import com.voracious.dragons.server.Main;
import com.voracious.dragons.server.User;

public class GameListPacket implements Packet {

    @Override
    public boolean wasCalled(Message message) {
        return message.toString().startsWith("GL:");
    }

    @Override
    public void process(Message message, ConnectionManager cm) {
        String msg = message.toString();
        ServerConnectionManager scm = (ServerConnectionManager) cm;
        
        User user = scm.getUserByID(msg.substring(msg.indexOf(":") + 1));
        if(user == null){
            cm.sendMessage(message.getSender(), "GLE:SessionId not in use");
            return;
        }
        
        String toSend = "GLS:";
        
        DBHandler db = Main.getDB();
        List<GameInfo> gameList = db.getGameList(user.getUsername());
        toSend += gameList.size() + ":";
        for(GameInfo i : gameList){
            toSend += ";" + i.toString();
        }
        
        scm.sendMessage(user, toSend);
    }

    @Override
    public boolean isString() {
        return true;
    }

}
