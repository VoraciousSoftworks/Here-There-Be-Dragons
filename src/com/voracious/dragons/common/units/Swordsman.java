package com.voracious.dragons.common.units;

import java.util.List;

import com.voracious.dragons.common.Vec2D;

public class Swordsman extends Unit {
	
	public static final byte ID = 2;
	private static final String filename="/squareUnit.png";
	private static final int [] numFrames={1};
	private static final int width=32;
	private static final int height=32;
	
	
	public Swordsman(List<Vec2D.Short> path, boolean whos) {
		super(filename, numFrames, width, height, path, whos, 100, 10, 5.0);
		
	}

	@Override
	public byte getUnitId() {
		return ID;
	}

}
