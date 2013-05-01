package com.voracious.dragons.client.graphics.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Drawable;
import com.voracious.dragons.client.graphics.Sprite;

public class UnitMenu implements Drawable{
	
	
	//Ignore this whole thing right now. Just the whole thing.
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, Game.HEIGHT-50, Game.WIDTH, 50);
		int numBaseUnit = 0;
		
		//Text BUC = new Text(Integer.toString(baseUnitCost));
		//BUC.setLocation(5, Game.HEIGHT-50);
		//BUC.setColor(Color.WHITE);
		//BUC.draw(g);
		Sprite baseUnitIcon = new Sprite("/circleUnit.png");
		baseUnitIcon.draw(g, 5, Game.HEIGHT-35);
		
		Button baseUnitIncr = new Button("+", 40, Game.HEIGHT-45);
		baseUnitIncr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //numBaseUnit++;
            }
		});
	}

}
