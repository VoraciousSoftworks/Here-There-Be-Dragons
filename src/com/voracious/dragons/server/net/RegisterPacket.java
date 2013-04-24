package com.voracious.dragons.server.net;

import java.io.IOException;

import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.server.Crypto;
import com.voracious.dragons.server.Main;
import com.voracious.dragons.server.User;

public class RegisterPacket implements Packet {

    @Override
    public boolean wasCalled(Message message) {
        return message.toString().startsWith("R:");
    }

    @Override
    public void process(Message message, ConnectionManager cm) {
        ServerConnectionManager scm = (ServerConnectionManager) cm;
        String msg = message.toString().substring(2);
        int splitLoc = msg.indexOf(":");
        String username = msg.substring(0, splitLoc);
        String password = msg.substring(splitLoc + 1, msg.length());
        
        if(Main.getDB().getPasswordHash(username) == null){
            Main.getDB().registerUser(username, Crypto.getSaltedHash(password));
            ServerMessageProcessor.logger.info("User registered: " + username);
            try {
                User user = scm.getUserByIP(message.getSender().getRemoteAddress().toString());
                String session = Crypto.getSessionId(); 
                user.setUsername(username);
                user.setSessionId(session);
                user.setAuthenticated(true);
                scm.sendMessage(user, "RS:" + session);
            } catch (IOException e) {
                ServerMessageProcessor.logger.error("Couldn't authenticate", e);
                scm.sendMessage(message.getSender(), "E:Server error");
            }
        }else{
            scm.sendMessage(message.getSender(), "E:Username already registered");
            ServerMessageProcessor.logger.debug("Attempted to register taken username: " + username);
        }
    }

    @Override
    public boolean isString() {
        return true;
    }
}
