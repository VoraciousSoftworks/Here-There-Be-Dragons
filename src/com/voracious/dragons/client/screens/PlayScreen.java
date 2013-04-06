package com.voracious.dragons.client.screens;

import java.awt.Graphics2D;

import com.voracious.dragons.client.graphics.Screen;

public class PlayScreen extends Screen {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    
    public PlayScreen() {
        super(HEIGHT, WIDTH);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawLine(0, 0, width, height);
        g.drawRect(750, 15, 25, 25);
    }

    @Override
    public void tick() {
        this.setOffsetx(this.getOffsetx() + 1);
    }
}
