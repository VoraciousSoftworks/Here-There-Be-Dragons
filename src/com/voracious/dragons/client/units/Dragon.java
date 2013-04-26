package com.voracious.dragons.client.units;

import java.util.List;

import com.voracious.dragons.common.Vec2D;

public class Dragon extends Unit {
	
    public static byte ID = 1;
	private static final String filename="/circleUnit.png";
	private static final int [] numFrames={1};
	private static final int width=16;
	private static final int height=16;

	public Dragon(List<Vec2D.Short> path) {
		super(filename, numFrames, width, height, path);
	}

	@Override
	public byte getUnitId() {
		return ID;
	}

}
