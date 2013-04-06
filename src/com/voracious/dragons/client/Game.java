package com.voracious.dragons.client;

import java.awt.Canvas;
import java.awt.Dimension;

import org.apache.log4j.Logger;

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    public static final String NAME = "Here There Be Dragons";
    public static final int WIDTH = 720;
    public static final int HEIGHT = 480;
    public static final int FPS = 60;
    private static final boolean printFPS = true;

    private static Logger logger = Logger.getLogger(Game.class);
    private static Thread thread;
    private static boolean running = false;
    

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void render() {
    }

    public void tick() {
    }

    @Override
    public void run() {
        double unprocessedTicks = 0.0;
        double nsPerTick = 1000000000.0/FPS;
        long lastTime = System.nanoTime();
        boolean needsRender = true;
        
        long lastFpsTime = System.currentTimeMillis();
        int frameCount = 0;
        int tickCount = 0;
        
        while(running){
            long now = System.nanoTime();
            unprocessedTicks += (now - lastTime) / nsPerTick;
            lastTime = now;
            
            if(unprocessedTicks >= 1.0){
                tick();
                unprocessedTicks -= 1.0;
                needsRender = true;
                tickCount++;
            }
            
            if(needsRender){
                render();
                needsRender = false;
                frameCount++;
            }
            
            if(printFPS && System.currentTimeMillis() - lastFpsTime > 1000){
                System.out.println(frameCount + " fps; " + tickCount + " tps");
                frameCount = 0;
                tickCount = 0;
                lastFpsTime = System.currentTimeMillis();
            }
        }
    }

    public void init() {
        logger.debug("init");
        thread = new Thread(this);
    }

    public void start() {
        logger.debug("start");
        running = true;
        thread.start();
    }

    public void stop() {
        logger.debug("stop");
        running = false;
    }
}
