package com.voracious.dragons.client.towers;

import java.util.ArrayList;

import com.voracious.dragons.client.graphics.Entity;
import com.voracious.dragons.client.units.Unit;

//TODO make abstract again
public class Tower extends Entity {
	
	private static final String filename="/tower.png";
	private static final int[] numFrames={1};
	private static final int width=16;
	private static final int height=16;
	private int range=8;
	private int attack=1;
	private int hp=25;

    public Tower() {
        super(filename, numFrames, width, height);
    }
    
    /**
     * @return the id for this tower type, it should be unique
     */
  //TODO make abstract again
    public byte getTowerId(){return 1;};
    
    
    /**
	 * @return the range
	 */
	public int getRange() {
		return range;
	}

	/**
	 * @param range the range to set
	 */
	public  void setRange(int range) {
		this.range = range;
	}
	
	public void setHP(int newHP){
		this.hp=newHP;
	}

	public int getHP(){
		return this.hp;
	}

	public void attackUnit(Unit victem){
    	victem.setHP((victem.getHP())-attack);
    }
}
