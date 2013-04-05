package com.voracious.dragons;

import java.awt.Canvas;

public class Game extends Canvas {
    private static final long serialVersionUID = 1L;
    
    public static final String NAME = "Here There Be Dragons";

    public void init() {
        System.out.println("init");
    }

    public void start() {
        System.out.println("start");
    }

    public void stop() {
        System.out.println("stop");
    }
}
