package com.voracious.dragons.server;

import com.voracious.dragons.common.Turn;

public class Main {
    public static void main(String[] args){
        System.out.println("server started");
        
        Turn test = new Turn(true);
        test.createUnit((byte)2, (short)10);
        test.createUnit((byte)32, (short)14);
        test.createUnit((byte)45, (short)17);
        test.createUnit((byte)90, (short)18);
        
        System.out.println("turn: " + test.toString());
    }
}
