package com.voracious.dragons.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class ConnectionManager implements Runnable {
	public static final int CHANNEL_WRITE_SLEEP = 200;
	public static final int CHANNEL_READ_SLEEP = 200;
	
	private Logger logger = Logger.getLogger(ConnectionManager.class);

	private BlockingQueue<Message> messageQueue;
	private Selector readSelector;
	private ByteBuffer writeBuffer;
	private ByteBuffer readBuffer;

	public ConnectionManager() {
		try {
			readSelector = Selector.open();
		} catch (IOException e) {
			logger.error("Could not open new connection", e);
		}
		
		writeBuffer = ByteBuffer.allocateDirect(255);
		readBuffer = ByteBuffer.allocateDirect(255);
		messageQueue = new LinkedBlockingQueue<Message>();
	}
	
	public void sendMessage(SocketChannel sc, String message) {
		prepWriteBuffer(Message.format(message));
		writeBytes(sc);
	}

	public void sendMessage(SocketChannel sc, byte[] message) {
		prepWriteBuffer(Message.format(message));
		writeBytes(sc);
	}
	
	private void writeBytes(SocketChannel sc) {
		long nbytes = 0;
		long toWrite = writeBuffer.remaining();
		try {
			while (nbytes != toWrite) {
				nbytes += sc.write(writeBuffer);

				try {
					Thread.sleep(CHANNEL_WRITE_SLEEP);
				} catch (InterruptedException e) {}
			}
		} catch (IOException e) {
			logger.error("Could not send message", e);
		}
		
		writeBuffer.rewind();
	}

	private void prepWriteBuffer(byte[] message) {
		writeBuffer.clear();
		writeBuffer.put(message);
		writeBuffer.flip();
	}

	public void readMessages(){
		try {
			readSelector.selectNow();
			Set<SelectionKey> readKeys = readSelector.selectedKeys();
			Iterator<SelectionKey> it = readKeys.iterator();
			while(it.hasNext()){
				SelectionKey key = it.next();
				it.remove();
				
				SocketChannel channel = (SocketChannel) key.channel();
				readBuffer.clear();
				long nbytes = channel.read(readBuffer);
				if(nbytes == -1){
					logger.info("Disconnect: " + channel.socket().getInetAddress() + " end of stream.");
					channel.close();
					
					Message message = new Message(channel, "eos".getBytes());
					message.setDisconnecting(true);
					messageQueue.offer(message);
				}else{
					ByteArrayOutputStream bos = (ByteArrayOutputStream) key.attachment();
					readBuffer.flip();
					byte[] msg = new byte[(int) nbytes]; 
					readBuffer.get(msg);
					readBuffer.clear();
					
					bos.write(msg);
					
					String result = "binary: ";
					byte[] bytes = bos.toByteArray();
					for(int i=0; i<bytes.length; i++){
						result += Integer.toString(bytes[i], 16) + " ";
					}
					System.out.println(result);
					
					boolean hasEOT = false;
					for(int i=0; i<msg.length; i++){
						if(msg[i] == Message.END_OF_TRANSMISSION) {
							hasEOT = true;
							break;
						}
					}
					
					if(hasEOT){
						Message message = new Message(channel, bos.toByteArray());
						bos.reset();
						logger.debug("Recieved message. From: " + channel.socket().getInetAddress() + " Contents: " + message.toString());
						
						if(message.toString() == "quit\n"){
							logger.info("Disconnect: " + channel.socket().getInetAddress() + " quitting.");
						}
						
						messageQueue.offer(message);
					}
				}
			}
		} catch (IOException e) {
			logger.warn("Error during read", e);
		}
	}
	
	public Selector getReadSelector() {
		return readSelector;
	}
	
	@Override
	public void run() {
		while(true){
			readMessages();
		}
	}
}
