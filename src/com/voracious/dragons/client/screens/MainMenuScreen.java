package com.voracious.dragons.client.screens;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import org.apache.log4j.Logger;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Screen;
import com.voracious.dragons.client.graphics.Sprite;
import com.voracious.dragons.client.graphics.ui.Button;
import com.voracious.dragons.client.utils.InputHandler;

public class MainMenuScreen extends Screen {
	public static final int WIDTH = 2160/3;
    public static final int HEIGHT = 1440/3;
    private static Logger logger = Logger.getLogger(Game.class);
    private Button playTurn,playNew,spectate,stats;
    private Sprite background;
    
    
	public MainMenuScreen() {
		super(WIDTH, HEIGHT);
		
		
		this.playTurn=new Button("Play Existing Game",235,120);
		this.playTurn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		this.playNew=new Button("Play New Game",410,120);
		this.playTurn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO go to a screen to choose new game
			}
		});
		this.spectate=new Button("Spectate a Game",250,220);
		this.playTurn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO go to a screen to choose the existing game to watch
			}
		});
		this.stats=new Button("See Stats",425,220);
		this.playTurn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO go to a screen to see the stats
			}
		});
		
		
		this.setBackground(new Sprite("/mainMenuBackground.png"));
		InputHandler.registerScreen(this);
	}

	@Override
	public void render(Graphics2D g) {
		this.getBackground().draw(g, 0, 0);
		this.playTurn.draw(g);
		this.playNew.draw(g);
		this.spectate.draw(g);
		this.stats.draw(g);
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub

	}

	 @Override
	 public void mouseClicked(MouseEvent e){
		 int ex=e.getX();
		 int ey=e.getY();
		 this.playTurn.mouseClicked(ex, ey);
		 this.playNew.mouseClicked(ex, ey);
		 this.spectate.mouseClicked(ex, ex);
		 this.stats.mouseClicked(ex, ey);
		 
	 }
	
	
	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public static void setLogger(Logger logger) {
		MainMenuScreen.logger = logger;
	}

	/**
	 * @return the background
	 */
	public Sprite getBackground() {
		return background;
	}

	/**
	 * @param background the background to set
	 */
	public void setBackground(Sprite background) {
		this.background = background;
	}

}
