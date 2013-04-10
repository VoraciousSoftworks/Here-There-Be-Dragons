package com.voracious.dragons.client.towers;

import com.voracious.dragons.client.graphics.Entity;

public abstract class Tower extends Entity {

    public Tower(String filename, int[] numFrames, int width, int height) {
        super(filename, numFrames, width, height);
    }
    
    /**
     * @return the id for this tower type, it should be unique
     */
    public abstract byte getTowerId();
}
