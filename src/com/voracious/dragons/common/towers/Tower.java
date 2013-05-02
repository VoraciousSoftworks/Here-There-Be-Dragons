package com.voracious.dragons.common.towers;

import com.voracious.dragons.client.graphics.Entity;
import com.voracious.dragons.common.Vec2D;
import com.voracious.dragons.common.units.Unit;

//TODO make abstract again
public class Tower extends Entity {
	
    private static final int ID = 0;
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
    
    public static Tower makeTower(int id, Vec2D.Short pos, boolean whos){
        Tower result = null;
        
        switch(id){
        case Tower.ID:
            result = new Tower(whos);
            result.setPos(pos);
            break;
        }
        
        return result;
    }

    public static Tower makeTower(String towerStr){
        String[] ts = towerStr.split("|");
        Tower result = null;
        switch(Integer.parseInt(ts[0])){
        case Tower.ID:
            result = new Tower(Integer.parseInt(ts[1]) == 1);
            break;
        }
        
        result.setX(Double.parseDouble(ts[2]));
        result.setY(Double.parseDouble(ts[3]));
        result.setHP(Integer.parseInt(ts[4]));
        
        return result;
    }
    
    public String toString(){
        String result = this.getTowerId() + "|";
        result += (isPlayer1 ? 1 : 0) + "|";
        result += this.getX() + "|";
        result += this.getY() + "|";
        result += this.getHP();
        
        return result;
    }
    
    public boolean isPlayer1(){
        return isPlayer1;
    }
    
    /**
     * @return the id for this tower type, it should be unique
     */
    //TODO make abstract again
    public byte getTowerId(){return ID;};
    
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
	
	public void takeDamage(int attack){
		if(attack<0){
		}
		else{
			this.setHP(this.getHP()-attack);
		}
	}
}
