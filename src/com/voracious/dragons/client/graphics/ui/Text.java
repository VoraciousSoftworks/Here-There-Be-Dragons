package com.voracious.dragons.client.graphics.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.voracious.dragons.client.graphics.Drawable;

public class Text implements Drawable {

	private int x, y;
	private String text;
	private BufferedImage image;
	private boolean needsRefresh; 
	
	public Text(String text, int x, int y){
		this.x = x;
		this.y = y;
		this.text = text;
		needsRefresh = true;
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(needsRefresh){
			int height = g.getFontMetrics().getHeight();
			int width = g.getFontMetrics().stringWidth(text);
			
			image = new BufferedImage(BufferedImage.TYPE_INT_ARGB, width, height);
			Graphics2D ig = ((Graphics2D)image.getGraphics());
			ig.setFont(g.getFont());
			ig.drawString(text, 0, 0);
			
			needsRefresh = false;
		}
		
		g.drawImage(image, null, x, y);
	}

	public void setText(String text){
		this.text = text;
		needsRefresh = true;
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
}
