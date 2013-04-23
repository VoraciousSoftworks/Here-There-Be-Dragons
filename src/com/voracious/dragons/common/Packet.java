package com.voracious.dragons.common;

import com.voracious.dragons.server.net.ServerConnectionManager;


public interface Packet {
    public boolean wasCalled(Message message);
    public void process(Message message, ServerConnectionManager scm);
    public boolean isString();
}
