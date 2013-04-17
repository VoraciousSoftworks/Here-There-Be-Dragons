package com.voracious.dragons.common;

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

	private BlockingQueue<ByteBuffer> messageQueue;
	private Selector readSelector;
	private ByteBuffer writeBuffer;
	private ByteBuffer readBuffer;

	public ConnectionManager() {
		try {
			readSelector = Selector.open();
		} catch (IOException e) {
			logger.error("Could not open new connection", e);
		}
		
		writeBuffer = ByteBuffer.allocate(255);
		messageQueue = new LinkedBlockingQueue<ByteBuffer>();
	}

	public void sendMessage(SocketChannel sc, String message) {
		sendMessage(sc, (message + "\n").getBytes());
	}

	public void sendMessage(SocketChannel sc, byte[] message) {
		prepWriteBuffer(message);

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
		/*try {
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
					logger.info("Disconnect:" + channel.socket().getInetAddress() + " end of stream");
					channel.close();
					
					
				}
			}
		} catch (IOException e) {
			logger.warn("Error during read", e);
		}*/
	}
	
	@Override
	public void run() {
		while(true){
			readMessages();
		}
	}
}
