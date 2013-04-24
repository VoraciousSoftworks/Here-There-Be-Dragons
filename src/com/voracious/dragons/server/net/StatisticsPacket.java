package com.voracious.dragons.server.net;

import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.common.Statistics;
import com.voracious.dragons.server.DBHandler;
import com.voracious.dragons.server.Main;
import com.voracious.dragons.server.User;

public class StatisticsPacket implements Packet {

    @Override
    public boolean wasCalled(Message message) {
        return message.toString().startsWith("PS:");
    }

    @Override
    public void process(Message message, ConnectionManager cm) {
        ServerConnectionManager scm = (ServerConnectionManager) cm;
        
        String msg = message.toString().substring(3);
        char type = msg.charAt(0);
        String sessionId = msg.substring(2);
        User user = scm.getUserByID(sessionId);
        DBHandler db = Main.getDB();
        switch(type){
        case Statistics.FINISHED_CODE:
            //TODO: db.getFinishedGames() or the equivalent
            break;
        }
    }

    @Override
    public boolean isString() {
        return true;
    }
}
