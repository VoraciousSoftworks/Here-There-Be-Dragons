package com.voracious.dragons.client.units;

public class Dragon extends Unit {
	
	private static final String filename="dragon.png";
	private static final int [] numFrames={1};
	private static final int width=16;
	private static final int height=16;

	public Dragon() {
		super(filename, numFrames, width, height);
	}

	@Override
	public byte getUnitId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
