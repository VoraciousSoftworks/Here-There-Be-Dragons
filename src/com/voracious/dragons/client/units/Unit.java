package com.voracious.dragons.client.units;

import com.voracious.dragons.client.graphics.Entity;

public abstract class Unit extends Entity {
	
    public Unit(String filename, int[] numFrames, int width, int height) {
        super(filename, numFrames, width, height);
    }
    
    /**
     * @return the id of this unit type, it should be unique
     */
    public abstract byte getUnitId();
    
    public void setWayPoint(Short wayP){
    	
    }
}
