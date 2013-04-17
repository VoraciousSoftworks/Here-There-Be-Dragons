package com.voracious.dragons.client.towers;

import com.voracious.dragons.client.graphics.Entity;

public abstract class Tower extends Entity {
	
	private static final String filename="tower.png";
	private static final int[] numFrames={1};
	private static final int width=16;
	private static final int height=16;

    public Tower() {
        super(filename, numFrames, width, height);
    }
    
    /**
     * @return the id for this tower type, it should be unique
     */
    public abstract byte getTowerId();
}
