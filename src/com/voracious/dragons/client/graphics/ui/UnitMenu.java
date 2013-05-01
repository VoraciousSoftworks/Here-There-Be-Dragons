package com.voracious.dragons.client.graphics.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Drawable;
import com.voracious.dragons.client.graphics.Sprite;

public class UnitMenu implements Drawable{
	
    public static final int baseUnitCost = 100;
    public static int numBaseUnit = 0; //Keeps track of the number of base units that the player is about to make.
    public Text BUC;
    public Text numBaseUnitText;
    public Button baseUnitIncr;
    public Button baseUnitDecr;
    
    public UnitMenu(){
    	BUC = new Text(Integer.toString(baseUnitCost));
    	BUC.setLocation(5, Game.HEIGHT-50);
		BUC.setColor(Color.WHITE);
		
		numBaseUnitText = new Text(Integer.toString(numBaseUnit));
		
		baseUnitIncr = new Button("+", 40, Game.HEIGHT-45);
		baseUnitDecr = new Button("-", 40, Game.HEIGHT-5);
		baseUnitIncr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                numBaseUnit++;
            }
		});
		baseUnitDecr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                numBaseUnit--;
            }
		});
		
		
		
    }
	
	//Ignore this whole thing right now. Just the whole thing.
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, Game.HEIGHT-50, Game.WIDTH, 50);
		Sprite baseUnitIcon = new Sprite("/circleUnit.png");
		numBaseUnitText.setText(Integer.toString(numBaseUnit));
		baseUnitIcon.draw(g, 5, Game.HEIGHT-35);
		BUC.draw(g);
		baseUnitIncr.draw(g);
		baseUnitDecr.draw(g);
	}
	
	public void reset(){
		numBaseUnit=0;
	}

}
