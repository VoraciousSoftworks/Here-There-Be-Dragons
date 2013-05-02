package com.voracious.dragons.server.net;

import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.server.Main;
import com.voracious.dragons.server.User;

public class ServerPlayGamePacket implements Packet {

    @Override
    public boolean wasCalled(Message message) {
        return message.toString().startsWith("PG:");
    }

    @Override
    public void process(Message message, ConnectionManager cm) {
        ServerConnectionManager scm = (ServerConnectionManager) cm;
        String[] msg = message.toString().substring(3).split(":");
        
        int gid = Integer.parseInt(msg[0]);
        
        User user = scm.getUserByID(msg[1]);
        if(Main.getDB().isInGame(user.getUsername(), gid)){
            user.setCurrentGame(gid);
            user.setPlaying(true);
            scm.sendMessage(message.getSender(), "PGS:" + Main.getDB().getGameState(gid));
        }else{
            scm.sendMessage(message.getSender(), "PGE:Error, you are not in that game");
        }
    }

    @Override
    public boolean isString() {
        return true;
    }
}
