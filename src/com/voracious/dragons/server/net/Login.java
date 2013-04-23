package com.voracious.dragons.server.net;

import java.io.IOException;

import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.server.Crypto;
import com.voracious.dragons.server.Main;
import com.voracious.dragons.server.User;

public class Login implements Packet {
    
    @Override
    public boolean wasCalled(Message message) {
        return message.toString().startsWith("L:");
    }

    @Override
    public void process(Message message, ServerConnectionManager scm) {
        String msg = message.toString();
        msg = msg.substring(2);
        int splitLoc = msg.indexOf(":");
        
        String username = msg.substring(0, splitLoc);
        String password =  msg.substring(splitLoc + 1, msg.length());
        
        String storedHash = Main.getDB().getPasswordHash(username);
        if(storedHash != null){
            if(Crypto.check(password, storedHash)){
                try {
                    User user = scm.getUser(message.getSender().getRemoteAddress().toString());
                    if(user != null){
                        String session = Crypto.getSessionId();
                        user.setSessionId(session);
                        user.setUsername(username);
                        user.setAuthenticated(true);
                        scm.sendMessage(user, "LS:" + session);
                        ServerMessageProcessor.logger.info("User logged in:" + username);
                    }else{
                        scm.sendMessage(user, "E:Connection error");
                    }
                } catch (IOException e) {
                    ServerMessageProcessor.logger.error("Couldn't authenticate", e);
                    scm.sendMessage(message.getSender(), "E:Server error");
                }
            }else{
                scm.sendMessage(message.getSender(), "E:Invalid password");
                ServerMessageProcessor.logger.debug("Invalid password tried");
            }
        }else{
            scm.sendMessage(message.getSender(), "E:Invalid username");
            ServerMessageProcessor.logger.debug("Invalid username tried: " + username);
        }
    }

    @Override
    public boolean isString() {
        return true;
    }
}
