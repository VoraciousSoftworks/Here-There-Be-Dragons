package com.voracious.dragons.common.units;

import java.util.List;

import com.voracious.dragons.common.Vec2D;


public class BatteringRam extends Unit {

	public static final byte ID = 3;
	private static final String filename="/triforceUnit.png";
	private static final int [] numFrames={1};
	private static final int width=32;
	private static final int height=32;
	
	public BatteringRam(List<Vec2D.Short> path, boolean whos){
		super(filename, numFrames, width, height, path, whos, 125, 15, 3.0);
		
	}
	
	
	@Override
	public byte getUnitId() {
		return ID;
	}

}
