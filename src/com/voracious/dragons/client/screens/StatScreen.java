package com.voracious.dragons.client.screens;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Screen;
import com.voracious.dragons.client.graphics.Sprite;
import com.voracious.dragons.client.graphics.ui.Button;
import com.voracious.dragons.client.net.ClientConnectionManager;
import com.voracious.dragons.client.net.Statistics;
import com.voracious.dragons.client.utils.InputHandler;

public class StatScreen extends Screen {
    public static final int ID = 3;
	public static final int WIDTH = 720;
    public static final int HEIGHT = 480;
    
    private Button returnButton;
    private Sprite background;
    
    private int finished,current,wins,losses;
    private double winRate,lossRate,aveTurnsPerGame;
    private long timeToMakeTurn;
    
	public StatScreen() {
		super(WIDTH, HEIGHT);
		
		background = new Sprite("/mainMenuBackground.png");
		returnButton=new Button("Back",0,0);
		returnButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.setCurrentScreen(MainMenuScreen.ID);
			}
		});
	}
	
	public void onStatRecieved(char type, String data){
	    switch(type){
	    case Statistics.FINISHED_CODE:
	        this.finished = Integer.parseInt(data);
	        break;
	    case Statistics.CURRENT_CODE:
	        this.current = Integer.parseInt(data);
	        break;
	    case Statistics.WINS_CODE:
	        this.wins = Integer.parseInt(data);
	        break;
	    case Statistics.LOSSES_CODE:
	        this.losses = Integer.parseInt(data);
	        break;
	    case Statistics.WIN_RATE_CODE:
	        this.winRate = Double.parseDouble(data);
	        break;
	    case Statistics.LOSS_RATE_CODE:
	        this.lossRate = Double.parseDouble(data);
	        break;
	    case Statistics.AVE_TURNS_PER_CODE:
	        this.aveTurnsPerGame = Double.parseDouble(data);
	        break;
	    case Statistics.TIME_TO_TURN_CODE:
	        this.timeToMakeTurn = Long.parseLong(data);
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
