/**
 * 
 */
package com.voracious.dragons.common.towers;

import com.voracious.dragons.client.graphics.Entity;

/**
 * This class will have health and change the image based on the remaining health of the castle.
 */
public class Castle extends Entity {

	public static String filename;
	public static String blueCast= "/castleFramesBlue.png";
	public static String redCast=  "/castleFrames.png";
	public static int[] numFrames = {4};
	public static int width = 300;
	public static int height = 300;
    private int levelofDamage=0;
	private double chp=100;
	private double fullhp=100;
	private double def=5;
	private boolean isRed=false;//true==red, false==blue
	
	public Castle(boolean isRED) {
		super(isRED ? redCast : blueCast, numFrames, width, height);
		if(isRED){
			filename=redCast;
		}
		else{
			filename=blueCast;
		}
	}

	//used to prevent unneeded calls of next frame. 
	public void takeDamage(double attk){
		//takes damage
		if(attk-def<0){
		}
		else{
			double damage=attk-def;
			chp-=damage;
			double percent=chp/fullhp;
			//changes the image if needed
			if(percent<=1.0&&percent>0.7){
				//nothing, stays at full heatlh
			}
			else if(percent<=0.7&&percent>0.4&&levelofDamage==0){
				this.nextFrame();
				levelofDamage++;
			}
			else if(percent<=0.4&&percent>0.1&&levelofDamage==1){
				this.nextFrame();
				levelofDamage++;
			}
			else if(levelofDamage==2){
				//rubble
			}
			System.out.println("PERCENT: "+percent+", levelDamage: "+levelofDamage);
		}
	}
	
	public double getMaxHP(){
	    return fullhp;
	}
	
	public void setMaxHP(double maxHP){
	    this.fullhp = maxHP;
	}
	
	public double getHP(){
		return this.chp;
	}
	
	public void setHP(double hp){
		this.chp=hp;
	}

	public double getHPRatio(){
		return this.getHP()/this.fullhp;
	}
}
