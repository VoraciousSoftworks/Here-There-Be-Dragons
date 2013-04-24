package com.voracious.dragons.client.screens;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Screen;
import com.voracious.dragons.client.graphics.Sprite;
import com.voracious.dragons.client.graphics.ui.Button;
import com.voracious.dragons.client.graphics.ui.Text;
import com.voracious.dragons.client.net.ClientConnectionManager;
import com.voracious.dragons.client.net.StatisticsPacket;
import com.voracious.dragons.client.utils.InputHandler;
import com.voracious.dragons.common.Statistics;

public class StatScreen extends Screen {
    public static final int ID = 3;
    
    private Button returnButton;
    private Sprite background;
    
    private int finished,current,wins,losses;
    private double winRate,lossRate,aveTurnsPerGame;
    private long timeToMakeTurn;
    
    private Text finishedT,currentT,winsT,lossesT,winRateT,lossRateT,aveTurnPerGameT,timeToMakeTurnT;
    
	public StatScreen() {
		super(Game.WIDTH, Game.HEIGHT);
		
		background = new Sprite("/mainMenuBackground.png");
		returnButton=new Button("Back",0,0);
		returnButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.setCurrentScreen(MainMenuScreen.ID);
			}
		});
		String mess="Getting Data";
		finishedT=new Text(mess,235,120);
		currentT=new Text(mess,410,120);
		winsT=new Text(mess,235,220);
		lossesT=new Text(mess,410,220);
		winRateT=new Text(mess,235,320);
		lossRateT=new Text(mess,410,320);
		aveTurnPerGameT=new Text(mess,235,420);
		timeToMakeTurnT=new Text(mess,410,420);
	}
	
	public void onStatRecieved(char type, String data){
	    switch(type){
	    case Statistics.FINISHED_CODE:
	        this.finished = Integer.parseInt(data);
	        this.finishedT.setText(this.finished+"");
	        break;
	    case Statistics.CURRENT_CODE:
	        this.current = Integer.parseInt(data);
	        this.currentT.setText(this.current+"");
	        break;
	    case Statistics.WINS_CODE:
	        this.wins = Integer.parseInt(data);
	        this.winsT.setText(this.wins+"");
	        break;
	    case Statistics.LOSSES_CODE:
	        this.losses = Integer.parseInt(data);
	        this.lossesT.setText(this.losses+"");
	        break;
	    case Statistics.WIN_RATE_CODE:
	        this.winRate = Double.parseDouble(data);
	        this.winRateT.setText(this.winRate+"");
	        break;
	    case Statistics.LOSS_RATE_CODE:
	        this.lossRate = Double.parseDouble(data);
	        this.lossRateT.setText(this.lossRate+"");
	        break;
	    case Statistics.AVE_TURNS_PER_CODE:
	        this.aveTurnsPerGame = Double.parseDouble(data);
	        this.aveTurnPerGameT.setText(this.aveTurnsPerGame+"");
	        break;
	    case Statistics.TIME_TO_TURN_CODE:
	        this.timeToMakeTurn = Long.parseLong(data);
	        this.timeToMakeTurnT.setText(this.timeToMakeTurn+"");
	        break;
	    }
	}
	
	public void requestData(){
	    ClientConnectionManager ccm = Game.getClientConnectionManager();
        ccm.sendMessage("PS:" + Statistics.FINISHED_CODE + ":" + ccm.getSessionId());
        ccm.sendMessage("PS:" + Statistics.CURRENT_CODE + ":" + ccm.getSessionId());
        ccm.sendMessage("PS:" + Statistics.WINS_CODE + ":" + ccm.getSessionId());
        ccm.sendMessage("PS:" + Statistics.LOSSES_CODE + ":" + ccm.getSessionId());
        ccm.sendMessage("PS:" + Statistics.WIN_RATE_CODE + ":" + ccm.getSessionId());
        ccm.sendMessage("PS:" + Statistics.LOSS_RATE_CODE + ":" + ccm.getSessionId());
        ccm.sendMessage("PS:" + Statistics.AVE_TURNS_PER_CODE + ":" + ccm.getSessionId());
        ccm.sendMessage("PS:" + Statistics.TIME_TO_TURN_CODE + ":" + ccm.getSessionId());
	}
	
	@Override
	public void start(){
		InputHandler.registerScreen(this);
	}
	
	@Override
	public void stop(){
		InputHandler.deregisterScreen(this);
	}


	@Override
	public void render(Graphics2D g) {
		background.draw(g, 0, 0);
		this.returnButton.draw(g);
		finishedT.draw(g);
		currentT.draw(g);
		winsT.draw(g);
		lossesT.draw(g);
		winRateT.draw(g);
		lossRateT.draw(g);
		aveTurnPerGameT.draw(g);
		timeToMakeTurnT.draw(g);
	}

	@Override
	public void tick() {
	}

	@Override
	public void mouseClicked(MouseEvent e){
		int ex=e.getX();
		int ey=e.getY();
		this.returnButton.mouseClicked(ex, ey);
	}

    @Override
    public int getId() {
        return ID;
    }
}
