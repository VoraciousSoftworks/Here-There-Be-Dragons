package com.voracious.dragons.client.net;

import com.voracious.dragons.client.screens.LoginScreen;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;

public class Authenticate implements Packet {

    @Override
    public boolean wasCalled(Message message) {
        String msg = message.toString();
        return msg.equals("Hello!") || msg.startsWith("RS:") || msg.startsWith("LS:") || msg.startsWith("LRE:");
    }

    @Override
    public void process(Message message, ConnectionManager cm) {
        ClientConnectionManager ccm = (ClientConnectionManager) cm;
        String msg = message.toString();
        
        if(msg.equals("Hello!")){
            String m = "";
            
            if(LoginScreen.isRegistering()){
                m += "R:";
            }else{
                m += "L:";
            }
            
            m += LoginScreen.getUsername() + ":" + LoginScreen.getPassword();
            
            ccm.sendMessage(m);
        }else if(msg.startsWith("RS:") || msg.startsWith("LS:")){
            if(!LoginScreen.hasLoggedIn()){
                ccm.setSessionId(msg.substring(2));
                LoginScreen.onSuccess();
            }
        }else if(msg.startsWith("LRE:")){
            if(!LoginScreen.hasLoggedIn()){
                LoginScreen.onFailure(msg.substring(2));
            }
        }
    }

    @Override
    public boolean isString() {
        return true;
    }

}
