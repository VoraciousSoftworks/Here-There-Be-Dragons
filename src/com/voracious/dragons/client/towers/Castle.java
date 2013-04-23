/**
 * 
 */
package com.voracious.dragons.client.towers;

import com.voracious.dragons.client.graphics.Entity;

/**
 * This class will have health and change the image based on the remaining health of the castle.
 */
public class Castle extends Entity {

	public static String filename = "/castleFull.png";
	public static int[] numFrames = {1};
	public static int width = 300;
	public static int height = 300;
	private double chp=100;
	private double fullhp=100;
	private double def=5;
	
	public Castle() {
		super(filename, numFrames, width, height);
	}
	
	public Castle(String file, int HP) {
		super(file, numFrames, width, height);
		chp=HP;
	}
	
	public void takeDamage(double attk){
		double damage=def-attk;
		chp-=damage;
		double percent=chp/fullhp;
		if(percent<=1.0&&percent>0.7){
			//nothing, stays at full heatlh
		}
		else if(percent<=0.7&&percent>0.4){
			//change to a semi damaged castle
		}
		else if(percent<=0.4&&percent>0.1){
			//almost gone
		}
		else{
			//rubble
		}
	}
	
	public double getCHP(){
		return this.chp;
	}
}
