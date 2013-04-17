package com.voracious.dragons.client.screens;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Screen;
import com.voracious.dragons.client.graphics.Sprite;
import com.voracious.dragons.client.graphics.ui.Button;
import com.voracious.dragons.client.utils.InputHandler;

public class MainMenuScreen extends Screen {
    private Button playTurn,playNew,spectate,stats;
    private Sprite background;
    
    
	public MainMenuScreen() {
		super(Game.WIDTH, Game.HEIGHT);
		
		
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
		
		
		background = new Sprite("/mainMenuBackground.png");
		InputHandler.registerScreen(this);
	}

	@Override
	public void render(Graphics2D g) {
		background.draw(g, 0, 0);
		this.playTurn.draw(g);
		this.playNew.draw(g);
		this.spectate.draw(g);
		this.stats.draw(g);
		
	}

	@Override
	public void tick() {
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
}
