package com.voracious.dragons.client.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Screen;
import com.voracious.dragons.client.graphics.Sprite;
import com.voracious.dragons.client.graphics.ui.Button;
import com.voracious.dragons.client.graphics.ui.Text;
import com.voracious.dragons.client.net.ClientConnectionManager;
import com.voracious.dragons.client.utils.InputHandler;
import com.voracious.dragons.common.Statistics;

public class StatScreen extends Screen {
    public static final int ID = 3;
    public static final int borderPadding = 10;
    
    private Button returnButton;
    private Sprite background;
    
    private int finished,current,wins,losses;
    private double winRate,lossRate,aveTurnsPerGame;
    private long timeToMakeTurn;
    
    private List<Text> texts;
    Text finishedT,currentT,winsT,lossesT,winRateT,lossRateT,aveTurnPerGameT,timeToMakeTurnT;
    
	public StatScreen() {
		super(Game.WIDTH, Game.HEIGHT);
		
		background = new Sprite("/mainMenuBackground.png");
		returnButton=new Button("Back", borderPadding, this.getHeight() - borderPadding - Button.defaultPadding*2 - 13);
		returnButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.setCurrentScreen(MainMenuScreen.ID);
			}
		});
		texts = new ArrayList<>(16);

		String mess="Getting Data";
	
		texts.add(new Text("Games Played: ", borderPadding*2, borderPadding*2));
		finishedT = new Text(mess, texts.get(0).getWidth() + borderPadding*2, texts.get(0).getY());
		texts.add(finishedT);
		
		texts.add(new Text("Games in Progress: ", borderPadding*2, borderPadding*3 + finishedT.getHeight()));
		currentT = new Text(mess, texts.get(2).getWidth() + borderPadding*2, texts.get(2).getY());
		texts.add(currentT);
		
		texts.add(new Text("Games won: ", borderPadding*2, borderPadding + currentT.getHeight() + currentT.getY()));
		winsT = new Text(mess, borderPadding*2 + texts.get(4).getWidth(), texts.get(4).getY());
		texts.add(winsT);
		
		texts.add(new Text("Games lost: ", borderPadding*2, borderPadding + winsT.getHeight() + winsT.getY()));
		lossesT = new Text(mess, borderPadding*2 + texts.get(6).getWidth(), texts.get(6).getY());
		texts.add(lossesT);
		
		texts.add(new Text("Win ratio: ", borderPadding*2, borderPadding + lossesT.getHeight() + lossesT.getY()));
		winRateT = new Text(mess, borderPadding*2 + texts.get(8).getWidth(), texts.get(8).getY());
		texts.add(winRateT);
		
		texts.add(new Text("Loss ratio: ", borderPadding*2, borderPadding + winRateT.getHeight() + winRateT.getY()));
		lossRateT = new Text(mess, borderPadding*2 + texts.get(10).getWidth(), texts.get(10).getY());
		texts.add(lossRateT);
		
		texts.add(new Text("Average turns per game: ", borderPadding*2, borderPadding + lossRateT.getHeight() + lossRateT.getY()));
		aveTurnPerGameT = new Text(mess, borderPadding*2 + texts.get(12).getWidth(), texts.get(12).getY());
		texts.add(aveTurnPerGameT);
		
		texts.add(new Text("Average time between turns: ", borderPadding*2, borderPadding + aveTurnPerGameT.getHeight() + aveTurnPerGameT.getY()));
		timeToMakeTurnT = new Text(mess, borderPadding*2 + texts.get(14).getWidth(), texts.get(14).getY());
		texts.add(timeToMakeTurnT);
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
		requestData();
	}
	
	@Override
	public void stop(){
		InputHandler.deregisterScreen(this);
	}


	@Override
	public void render(Graphics2D g) {
		background.draw(g, 0, 0);
        g.setColor(new Color(0xCCCCCCCC, true));
		g.fillRect(borderPadding, borderPadding, this.getWidth() - borderPadding*2, this.getHeight() - borderPadding*3 - returnButton.getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(borderPadding, borderPadding, this.getWidth() - borderPadding*2, this.getHeight() - borderPadding*3 - returnButton.getHeight());
		
		this.returnButton.draw(g);
		for(Text t : texts){
		    t.draw(g);
		}
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
