package com.voracious.dragons.server.net;

import com.voracious.dragons.common.ConnectionManager;
import com.voracious.dragons.common.Message;
import com.voracious.dragons.common.Packet;
import com.voracious.dragons.common.Statistics;
import com.voracious.dragons.server.DBHandler;
import com.voracious.dragons.server.Main;
import com.voracious.dragons.server.User;

public class StatisticsPacket implements Packet {

    @Override
    public boolean wasCalled(Message message) {
        return message.toString().startsWith("PS:");
    }

    @Override
    public void process(Message message, ConnectionManager cm) {
        ServerConnectionManager scm = (ServerConnectionManager) cm;
        
        String msg = message.toString().substring(3);
        char type = msg.charAt(0);
        String sessionId = msg.substring(2);
        User user = scm.getUserByID(sessionId);
        DBHandler db = Main.getDB();
        switch(type){
        case Statistics.FINISHED_CODE:{
        	int games = db.numGames(user.getUsername(), 0);
        	scm.sendMessage(user,"PS:"+type+":"+games);
        	break;
        }
        case Statistics.CURRENT_CODE:{
        	int games = db.numGames(user.getUsername(), 1);
        	scm.sendMessage(user, "PS:"+type+":"+games);
        	break;
        }
        case Statistics.WINS_CODE:{
        	int winNum = db.countWins(user.getUsername());
        	scm.sendMessage(user, "PS:"+type+":"+winNum);
        	break;
        }
        case Statistics.LOSSES_CODE:{
        	int lossNum = db.numGames(user.getUsername(), 0) - db.countWins(user.getUsername());
        	scm.sendMessage(user, "PS:"+type+":"+lossNum);
        	break;
        }
        case Statistics.WIN_RATE_CODE:{
            int numWins = db.countWins(user.getUsername());
            int numFinishedGames = db.numGames(user.getUsername(), 0);
        	double winRate = numFinishedGames == 0 ? 0.0 : numWins/((double)numFinishedGames);
        	scm.sendMessage(user, "PS:"+type+":"+winRate);
        	break;
        }
        case Statistics.LOSS_RATE_CODE:{
            int numWins = db.countWins(user.getUsername());
            int numFinishedGames = db.numGames(user.getUsername(), 0);
            double winRate = numFinishedGames == 0 ? 0.0 : numWins/((double)numFinishedGames);
            
        	double loseRate = winRate == 0.0 ? 0.0 : 1 - winRate;
        	scm.sendMessage(user, "PS:"+type+":"+loseRate);
        	break;
        }
        case Statistics.AVE_TURNS_PER_CODE:{
        	double turns=db.aveTurns(user.getUsername());
        	scm.sendMessage(user, "PS:"+type+":"+turns);
        	break;
        }
        case Statistics.TIME_TO_TURN_CODE:{
        	long time=db.aveTime(user.getUsername());
        	scm.sendMessage(user, "PS:"+type+":"+time);
        	break;
        }
        }
    }

    @Override
    public boolean isString() {
        return true;
    }
}
