package com.voracious.dragons.server.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.MessageProcessor;
import com.voracious.dragons.common.Packet;

public class ServerMessageProcessor extends MessageProcessor {

	private ServerConnectionManager scm;
	public static Logger logger = Logger.getLogger(ServerMessageProcessor.class);
	private List<Packet> stringPackets;
	private List<Packet> binaryPackets;
	
	public ServerMessageProcessor(ServerConnectionManager scm, BlockingQueue<Message> messageQueue) {
		super(messageQueue);
		this.scm = scm;
		
		stringPackets = new ArrayList<>(2);
		stringPackets.add(new Login());
		stringPackets.add(new Register());
		
		binaryPackets = new ArrayList<>(0);
	}

	@Override
	public void processMessage(Message message) {
		if(message.isString()){
		    for(Packet p : stringPackets){
		        if(p.wasCalled(message)){
		            p.process(message, scm);
		            break;
		        }
		    }
		}else if(!message.isString()){
		    for(Packet p : binaryPackets){
                if(p.wasCalled(message)){
                    p.process(message, scm);
                    break;
                }
            }
		}else if(message.isDisconnecting()){
			try {
				scm.disconnectUser(message.getSender().getRemoteAddress().toString());
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
}
