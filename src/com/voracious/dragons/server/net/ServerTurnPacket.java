package com.voracious.dragons.server.net;

import com.voracious.dragons.client.screens.PlayScreen;
import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.GameState;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.common.Turn;
import com.voracious.dragons.server.Main;
import com.voracious.dragons.server.User;

public class ServerTurnPacket implements Packet{

	@Override
	public boolean wasCalled(Message message) {
		return message.getBytes()[0] == 7;
	}

	@Override
	public void process(final Message message, ConnectionManager cm) {
		final ServerConnectionManager scm = (ServerConnectionManager) cm;
		byte[] mbytes = message.getBytes();
		byte[] turn = new byte[mbytes.length-1];
		System.arraycopy(mbytes, 1, turn, 0, turn.length);
		final Turn newTurn = new Turn(turn);
		final String PID = scm.getUserByID(newTurn.getSessionId()).getUsername();
		if(PID == null)
		    return;
		
		boolean shouldSimulate = Main.getDB().insertTurn(newTurn.getGameId(), PID, newTurn.toString());
		
		if(shouldSimulate){
		    
		    final String otherPid = Main.getDB().getOtherPid(PID, newTurn.getGameId());
		    final User other = scm.getUserByName(otherPid);
    		if(other != null && other.isPlaying() && other.getCurrentGame() == newTurn.getGameId()){
    		    newTurn.setSessionId("//////////////////////////////////////////8=");
    		    byte[] safeTurn = newTurn.toBytes().array();
    		    byte[] toSend = new byte[safeTurn.length + 1];
    		    toSend[0] = 7;
    		    System.arraycopy(safeTurn, 0, toSend, 1, safeTurn.length);
    		    scm.sendMessage(other, toSend);
    		}
    		
    		final Turn othersTurn = Main.getDB().getLatestTurn(newTurn.getGameId(), otherPid);
    		if(othersTurn != null){
    		    final GameState gs = new GameState(Main.getDB().getGameState(newTurn.getGameId()));
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        gs.simulate(newTurn, othersTurn);
                        for(int i=0; i<PlayScreen.ticksPerTurn; i++){
                            gs.tick();
                            
                            try {
                                Thread.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        
                        gs.updateSeed();
                        scm.sendMessage(message.getSender(), "GS:" + gs.toString());
                        if(other != null && other.isPlaying() && other.getCurrentGame() == newTurn.getGameId()){
                            scm.sendMessage(other, "GS:" + gs.toString());
                        }
                        if(gs.isOver()){
                            Main.getDB().updateProgress(newTurn.getGameId());
                            String winner = "";
                            boolean win = gs.getP1Cast().getHP() <= 0;
                            if(newTurn.isPlayer1()){
                                if(win){
                                    winner = PID;
                                }else{
                                    winner = otherPid;
                                }
                            }else{
                                if(win){
                                    winner = otherPid;
                                }else{
                                    winner = PID;
                                }
                            }
                            
                            Main.getDB().insertWinner(newTurn.getGameId(), winner);
                        }
                        Main.getDB().updateGameState(newTurn.getGameId(), gs.toString());
                    }
                }).start();
    		    
        		othersTurn.setSessionId("//////////////////////////////////////////8=");
        		byte[] safeTurn = othersTurn.toBytes().array();
                byte[] toSend = new byte[safeTurn.length + 1];
                toSend[0] = 7;
                System.arraycopy(safeTurn, 0, toSend, 1, safeTurn.length);
        		scm.sendMessage(message.getSender(), toSend);
    		}
		}
	}

	@Override
	public boolean isString() {
		return false;
	}
}
