package com.voracious.dragons.client.towers;

import com.voracious.dragons.client.graphics.Entity;
import com.voracious.dragons.client.units.Unit;

//TODO make abstract again
public class Tower extends Entity {
	
	private static final String filename = "/tower.png";
	private static final int[] numFrames = {1};
	private static final int width = 16;
	private static final int height = 16;
	private int range = 8;
	private int attack = 1;
	private int hp = 25;
	private boolean isPlayer1;

    public Tower(boolean isPlayer1) {
        super(filename, numFrames, width, height);
        this.isPlayer1 = isPlayer1;
    }
    
    public boolean isPlayer1(){
        return isPlayer1;
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

	public void setHP(int newHP){
		this.hp=newHP;
	}

	public int getHP(){
		return this.hp;
	}

	public void attackUnit(Unit victim){
    	victim.setHP((victim.getHP()) - attack);
    }
}
