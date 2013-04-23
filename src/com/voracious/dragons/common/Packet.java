package com.voracious.dragons.common;

public interface Packet {
    
    /**
     * @param message the message to test
     * @return whether this message is meant to be processed by this packet or not
     */
    public boolean wasCalled(Message message);
    
    /**
     * Processes the packet message, only called after wasCalled returns true
     * The ConnectionManager object will be a ClientConnectionManager or a ServerConnectionManager depending on whether this is a server packet or a client packet
     * 
     * @param message the message to process
     * @param cm the connection manager
     */
    public void process(Message message, ConnectionManager cm);
    
    /**
     * @return whether this packet processes strings or binary data
     */
    public boolean isString();
}
