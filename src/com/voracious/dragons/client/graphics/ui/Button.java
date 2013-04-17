package com.voracious.dragons.client.graphics.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.voracious.dragons.client.graphics.Drawable;

public class Button implements Drawable {

	public static final Color defaultBackground = new Color(0xDDDDDD);
	public static final Color defaultBoarder = new Color(0xEEEEEE);
	public static final int defaultPadding = 5;
	
	private int x, y;
	private int width, height;
	private Text text;
	private BufferedImage image;
	
	public Button(String text) {
		this.text = new Text(text, defaultPadding, defaultPadding);

		width = this.text.getWidth() + defaultPadding*2;
		height = this.text.getHeight() + defaultPadding*2;
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig = (Graphics2D) image.getGraphics();
		
		ig.setColor(defaultBackground);
		ig.fillRect(0, 0, width, height);
		
		ig.setColor(defaultBoarder);
		ig.drawRect(0, 0, width, height);
		
		this.text.draw(ig);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.drawImage(image, null, x, y);
	}
}
