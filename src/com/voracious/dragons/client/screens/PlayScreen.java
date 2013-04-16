package com.voracious.dragons.client.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.apache.log4j.Logger;

import com.voracious.dragons.client.Game;
import com.voracious.dragons.client.graphics.Screen;
import com.voracious.dragons.client.utils.InputHandler;

public class PlayScreen extends Screen {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    private static Logger logger = Logger.getLogger(Game.class);
    
    public PlayScreen() {
        super(HEIGHT, WIDTH);
        
        InputHandler.registerButton(KeyEvent.VK_W);
        InputHandler.registerButton(KeyEvent.VK_A);
        InputHandler.registerButton(KeyEvent.VK_S);
        InputHandler.registerButton(KeyEvent.VK_D);
        InputHandler.registerScreen(this);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, width, height);
        g.drawRect(750, 15, 25, 25);
    }

    @Override
    public void tick() {
        if(InputHandler.isDown(KeyEvent.VK_W)){
            this.translate(0, -3);
        }else if(InputHandler.isDown(KeyEvent.VK_S)){
            this.translate(0, 3);
        }
        
        if(InputHandler.isDown(KeyEvent.VK_A)){
            this.translate(-3, 0);
        }else if(InputHandler.isDown(KeyEvent.VK_D)){
            this.translate(3, 0);
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        if(InputHandler.isDown(InputHandler.VK_MOUSE_2)){
            this.translate(InputHandler.getChangeInMouse());
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        if(InputHandler.isDown(InputHandler.VK_MOUSE_2)){
            InputHandler.setMouseMoveable(false);
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        if(!InputHandler.isDown(InputHandler.VK_MOUSE_2)){
            InputHandler.setMouseMoveable(true);
        }
    }
}
