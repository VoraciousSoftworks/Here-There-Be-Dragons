package com.voracious.dragons.client.units;

import java.util.List;

import com.voracious.dragons.client.graphics.Entity;
import com.voracious.dragons.client.towers.Castle;
import com.voracious.dragons.common.Vec2D;

public abstract class Unit extends Entity {
	
    public static double default_velocity = 3.0;
    private List<Vec2D.Short> path;
    private short goingTo;
    private boolean atEnd = false;
    private int HP=100;
    private int attack=5;
    private boolean isPlayer1;
    
    public Unit(String filename, int[] numFrames, int width, int height, List<Vec2D.Short> path, boolean whos) {
        super(filename, numFrames, width, height);
        this.path = path;
        this.goingTo = 1;
        Vec2D.Short init = path.get(0);
        Vec2D.Short next = new Vec2D.Short(path.get(1));
        this.setPos(init);
        
        this.isPlayer1=whos;
        
        next.div(Math.sqrt(next.dot(next)));
        next.y = -next.y;
        next.mult(default_velocity);
        this.setVelocity(next);
    }
    
    @Override
    public void tick(){
        super.tick();
        
        if(!atEnd){
            Vec2D.Short goingToPos = path.get(goingTo);
            Vec2D vel = this.getVelocity();
            
            if(vel.x > 0){
                if(this.getX() > goingToPos.x){
                    if(vel.y > 0){
                        if(this.getY() > goingToPos.y){
                            toNextNode();
                        }
                    }else{
                        if(this.getY() < goingToPos.y){
                            toNextNode();
                        }
                    }
                }
            }else{
                if(this.getX() < goingToPos.x){
                    if(vel.y > 0){
                        if(this.getY() > goingToPos.y){
                            toNextNode();
                        }
                    }else{
                        if(this.getY() < goingToPos.y){
                            toNextNode();
                        }
                    }
                }
            }
        }
    }
    
    public void toNextNode(){
        goingTo++;
        if(path.size() > goingTo){
            Vec2D.Short next = new Vec2D.Short(path.get(goingTo));
            next.div(Math.sqrt(next.dot(next)));
            next.y = -next.y;
            next.mult(default_velocity);
            this.setVelocity(next);
        }else{
            atEnd = true;
            this.setVelocity(new Vec2D.Double(0, 0));
        }
    }
    
    public abstract byte getUnitId();

	/**
	 * @return the hP
	 */
	public int getHP() {
		return HP;
	}

	/**
	 * @param hP the hP to set
	 */
	public void setHP(int hP) {
		HP = hP;
	}

	/**
	 * @return the attack
	 */
	public int getAttack() {
		return attack;
	}

	/**
	 * @param attack the attack to set
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public boolean getAtEnd(){
		return atEnd;
	}

	/**
	 * @return the isPlayer1
	 */
	public boolean isPlayer1() {
		return isPlayer1;
	}

	/**
	 * @param isPlayer1 the isPlayer1 to set
	 */
	public void setPlayer1(boolean isPlayer1) {
		this.isPlayer1 = isPlayer1;
	}
	
	public boolean getISPLAYER1(){
		return isPlayer1;
	}
}
