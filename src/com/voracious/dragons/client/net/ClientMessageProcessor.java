package com.voracious.dragons.client.net;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.MessageProcessor;
import com.voracious.dragons.common.Packet;

public class ClientMessageProcessor extends MessageProcessor {

	private ClientConnectionManager ccm;
	public static Logger logger = Logger.getLogger(ClientMessageProcessor.class);
	private List<Packet> stringPackets;
	private List<Packet> binaryPackets;
	
	public ClientMessageProcessor(ClientConnectionManager ccm, BlockingQueue<Message> messageQueue) {
		super(messageQueue);
		this.ccm = ccm;
		
		stringPackets = new ArrayList<>(3);
		stringPackets.add(new AuthenticatePacket());
		stringPackets.add(new StatisticsPacket());
		stringPackets.add(new GameListPacket());
		stringPackets.add(new ClientChallengeRequestPacket());
		stringPackets.add(new ClientPlayGamePacket());
		stringPackets.add(new GameStatePacket());
		
		binaryPackets = new ArrayList<>(1);
		binaryPackets.add(new ClientTurnPacket());
	}

	@Override
	public void processMessage(Message message) {
		if(message.isString()) {
		    for(Packet p : stringPackets) {
                if(p.wasCalled(message)){
                    p.process(message, ccm);
                    break;
                }
            }
		}else if(!message.isString()) {
		    for(Packet p : binaryPackets) {
		        if(p.wasCalled(message)){
		            p.process(message, ccm);
		            break;
		        }
		    }
		}
	}
}
