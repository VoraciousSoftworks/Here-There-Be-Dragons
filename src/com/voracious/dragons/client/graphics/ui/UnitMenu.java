package com.voracious.dragons.client.graphics.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Drawable;
import com.voracious.dragons.client.graphics.Sprite;
import com.voracious.dragons.client.screens.PlayScreen;
import com.voracious.dragons.common.units.BatteringRam;
import com.voracious.dragons.common.units.Dragon;
import com.voracious.dragons.common.units.Swordsman;

public class UnitMenu implements Drawable{
	
    public static final String baseUnitCost = "100R";
    public static final String swordUnitCost = "200R";
    public static final String dragonUnitCost = "300R";
    public static int numBaseUnit = 0;//Keeps track of the number of base units that the player is about to make.
    public static int numSwordUnit = 0;
    public static int numDragonUnit = 0;
    public PlayScreen theScreen;
    public Text BUC;
    public Text numBaseUnitText;
    public Text SUC;
    public Text numSwordUnitText;
    public Text DUC;
    public Text numDragonUnitText;
    public Button baseUnitIncr;
    public Button baseUnitDecr;
    public Button swordUnitIncr;
    public Button swordUnitDecr;
    public Button dragonUnitIncr;
    public Button dragonUnitDecr;
    
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
                theScreen.addUnit(BatteringRam.ID, (short)1);
            }
		});
		baseUnitDecr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(getBaseNum() > 0){
            		UnitMenu.decrementBaseNum();
            		theScreen.addUnit(BatteringRam.ID, (short)-1);
            	}   
            }
		});
		
		SUC = new Text(swordUnitCost);
    	SUC.setLocation(75, Game.HEIGHT-50);
		
		numSwordUnitText = new Text(Integer.toString(numSwordUnit));
		numSwordUnitText.setLocation(110, Game.HEIGHT-30);
		
		swordUnitIncr = new Button("+", 110, Game.HEIGHT-50);
		swordUnitDecr = new Button("-", 130, Game.HEIGHT-50);
		swordUnitIncr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                UnitMenu.incrementSwordNum();
                theScreen.addUnit(Swordsman.ID, (short)1);
            }
		});
		swordUnitDecr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(getSwordNum() > 0){
            		UnitMenu.decrementSwordNum();
            		theScreen.addUnit(Swordsman.ID, (short)-1);
            	}   
            }
		});
		
		DUC = new Text(dragonUnitCost);
    	DUC.setLocation(145, Game.HEIGHT-50);
		
		numDragonUnitText = new Text(Integer.toString(numDragonUnit));
		numDragonUnitText.setLocation(180, Game.HEIGHT-30);
		
		dragonUnitIncr = new Button("+", 180, Game.HEIGHT-50);
		dragonUnitDecr = new Button("-", 210, Game.HEIGHT-50);
		dragonUnitIncr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                UnitMenu.incrementDragonNum();
                theScreen.addUnit(Dragon.ID, (short)1);
            }
		});
		dragonUnitDecr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(getSwordNum() > 0){
            		UnitMenu.decrementDragonNum();
            		theScreen.addUnit(Dragon.ID, (short)-1);
            	}   
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
		
		Sprite swordUnitIcon = new Sprite("/SwordsmanIcon.png");
		numSwordUnitText.setColor(Color.WHITE);
		numSwordUnitText.setText(Integer.toString(numSwordUnit));
		numSwordUnitText.draw(g);
		swordUnitIcon.draw(g, 75, Game.HEIGHT-35);
		SUC.setColor(Color.WHITE);
		SUC.draw(g);
		swordUnitIncr.draw(g);
		swordUnitDecr.draw(g);
		
		Sprite dragonUnitIcon = new Sprite("/DragonIcon.png");
		numDragonUnitText.setColor(Color.WHITE);
		numDragonUnitText.setText(Integer.toString(numDragonUnit));
		numDragonUnitText.draw(g);
		dragonUnitIcon.draw(g, 145, Game.HEIGHT-35);
		DUC.setColor(Color.WHITE);
		DUC.draw(g);
		dragonUnitIncr.draw(g);
		dragonUnitDecr.draw(g);
	}
	
	public void reset(){
		numBaseUnit=0;
		numSwordUnit=0;
		numDragonUnit=0;
	}
	
	public static void incrementBaseNum(){
		UnitMenu.numBaseUnit++;
	}
	
	public static void decrementBaseNum(){
		UnitMenu.numBaseUnit--;
	}
	
	public static void incrementSwordNum(){
		UnitMenu.numSwordUnit++;
	}
	
	public static void decrementSwordNum(){
		UnitMenu.numSwordUnit--;
	}
	
	public static void incrementDragonNum(){
		UnitMenu.numDragonUnit++;
	}
	
	public static void decrementDragonNum(){
		UnitMenu.numDragonUnit--;
	}
	
	public void mouseClicked(int x, int y){
		baseUnitIncr.mouseClicked(x, y);
		baseUnitDecr.mouseClicked(x, y);
		swordUnitIncr.mouseClicked(x, y);
		swordUnitDecr.mouseClicked(x, y);
		dragonUnitIncr.mouseClicked(x, y);
		dragonUnitDecr.mouseClicked(x, y);
	}
	
	public int getBaseNum(){
		return numBaseUnit;
	}
	
	public int getSwordNum(){
		return numSwordUnit;
	}
	
	public int getDragonNum(){
		return numDragonUnit;
	}

}
