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
	
	
	public Castle() {
		super(filename, numFrames, width, height);
	}

}
