package com.voracious.dragons.client.graphics.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.voracious.dragons.client.graphics.Drawable;

public class Text implements Drawable {

	private int x, y;
	private Color color;
	private String text;
	private BufferedImage image;
	private boolean needsRefresh; 
	
	public Text(String text, int x, int y, Color c){
		this.x = x;
		this.y = y;
		this.color = c;
		this.text = text;
		needsRefresh = true;
	}
	
	public Text(){
		this.x = 0;
		this.y = 0;
		this.color = Color.BLACK;
		this.text = "No text set";
		needsRefresh=true;
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(needsRefresh){
			g.setColor(color);
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
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
