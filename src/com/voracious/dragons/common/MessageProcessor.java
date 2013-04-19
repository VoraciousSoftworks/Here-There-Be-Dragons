package com.voracious.dragons.common;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public abstract class MessageProcessor implements Runnable {
	
	private Logger logger = Logger.getLogger(MessageProcessor.class);
	private BlockingQueue<Message> messageQueue;
	private boolean shouldContinue = true;
	
	public MessageProcessor(BlockingQueue<Message> messageQueue) {
		this.messageQueue = messageQueue;
	}
	
	public abstract void processMessage(Message message);
	
	public void setShouldCountinue(boolean shouldContinue){
		this.shouldContinue = shouldContinue;
	}
	
	@Override
	public void run(){
		//TODO: should probably be a boolean at some point so we can gracefully stop
		while(shouldContinue) {
			try {
				processMessage(messageQueue.take());
			} catch (InterruptedException e) {
				logger.error("Could not process messages", e);
			}
		}
	}
}
