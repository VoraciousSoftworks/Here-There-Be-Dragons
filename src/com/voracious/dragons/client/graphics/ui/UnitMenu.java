package com.voracious.dragons.client.graphics.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Drawable;
import com.voracious.dragons.client.graphics.Sprite;
import com.voracious.dragons.client.screens.PlayScreen;

public class UnitMenu implements Drawable{
	
    public static final String baseUnitCost = "100R";
    public static int numBaseUnit = 0; //Keeps track of the number of base units that the player is about to make.
    public PlayScreen theScreen;
    public Text BUC;
    public Text numBaseUnitText;
    public Button baseUnitIncr;
    public Button baseUnitDecr;
    
    public UnitMenu(PlayScreen p){
    	theScreen = p;
    	BUC = new Text(baseUnitCost);
    	BUC.setLocation(5, Game.HEIGHT-50);
		
		numBaseUnitText = new Text(Integer.toString(numBaseUnit));
		numBaseUnitText.setLocation(40, Game.HEIGHT-30);
		
		baseUnitIncr = new Button("+", 40, Game.HEIGHT-50);
		baseUnitDecr = new Button("-", 60, Game.HEIGHT-50);
		baseUnitIncr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                UnitMenu.incrementBaseNum();
                theScreen.createUnit()
            }
		});
		baseUnitDecr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                UnitMenu.decrementBaseNum();
            }
		});
		
		
		
    }
	
    
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, Game.HEIGHT-50, Game.WIDTH, 50);
		Sprite baseUnitIcon = new Sprite("/circleUnit.png");
		numBaseUnitText.setColor(Color.WHITE);
		numBaseUnitText.setText(Integer.toString(numBaseUnit));
		numBaseUnitText.draw(g);
		baseUnitIcon.draw(g, 5, Game.HEIGHT-35);
		BUC.setColor(Color.WHITE);
		BUC.draw(g);
		baseUnitIncr.draw(g);
		baseUnitDecr.draw(g);
		
	}
	
	public void reset(){
		numBaseUnit=0;
	}
	
	public static void incrementBaseNum(){
		UnitMenu.numBaseUnit++;
	}
	
	public static void decrementBaseNum(){
		UnitMenu.numBaseUnit--;
	}
	
	public void mouseClicked(int x, int y){
		baseUnitIncr.mouseClicked(x, y);
		baseUnitDecr.mouseClicked(x, y);
	}

}
