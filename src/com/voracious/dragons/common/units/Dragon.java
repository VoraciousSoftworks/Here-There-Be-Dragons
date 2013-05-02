package com.voracious.dragons.common.units;

import java.util.List;

import com.voracious.dragons.common.Vec2D;

public class Dragon extends Unit {
	
    public static final byte ID = 1;
	private static final String filename="/circleUnit.png";
	private static final int [] numFrames={1};
	private static final int width=32;
	private static final int height=32;

	public Dragon(List<Vec2D.Short> path,boolean whos) {
		super(filename, numFrames, width, height, path, whos);
	}

	@Override
	public byte getUnitId() {
		return ID;
	}

}
