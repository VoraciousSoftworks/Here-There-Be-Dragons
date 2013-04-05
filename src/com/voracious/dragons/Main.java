package com.voracious.dragons;

import java.applet.Applet;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Main extends Applet implements WindowListener {
    private static final long serialVersionUID = 1L;
    private static Game game;

    public static void main(String[] args) {
        Frame window = new Frame(Game.NAME);
        game = new Game();
        game.init();
        window.add(game);

        window.addWindowListener(new Main());
        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);

        game.start();
    }
    
    @Override
    public void windowClosing(WindowEvent arg0) {
        game.stop();
        System.exit(0);
    }
    
    @Override
    public void init(){
        game = new Game();
        this.add(game);
        game.init();
    }
    
    @Override
    public void start(){
        game.start();
    }
    
    @Override
    public void stop(){
        game.stop();
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
    }
}
