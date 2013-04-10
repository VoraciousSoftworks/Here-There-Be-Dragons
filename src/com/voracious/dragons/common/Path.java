package com.voracious.dragons.common;

import java.util.ArrayList;


public class Path {
	
	private ArrayList<Vec2D.Short> pathList;
	
	public Path(Vec2D.Short start, Vec2D.Short end){
		//TODO hard code that player cannot add a way point if the path has a geometry, out side
		//add @ size-2, b/c @ 0 is start and @ size-1 is end
		pathList=new ArrayList<Vec2D.Short>();
		pathList.add(new Vec2D.Short(start.getx(),start.gety()));
		pathList.add(new Vec2D.Short(end.getx(),end.gety()));
		//made a copy b/c it's pass by reference and this will prevent it from being deleted when going out of scope
	}
	
	/*public void fillInList(){
		Vec2D.Short start=this.getPathList().get(0);
		Vec2D.Short end=this.getPathList().get(this.pathList.size()-1);
		short yDiff=(short) (end.gety()-start.gety());
		short xDiff=(short) (end.getx()-start.getx());
		int insertLoc=this.getPathList().size()-1;
		for(int i=0;i<100;i++){//The 100 is arbitrary for now
			this.pathList.add(insertLoc, new Vec2D.Short((start.getx()+((i*xDiff)/100)),start.gety()+((i*yDiff)/100)));
		}
		
	}*/
	
	/**
	 * Adds the new coordinate to the array list, but at index before the end point.
	 * @param mid is the new coordinate to add to the array list. 
	 */
	public void addMidpoint(Vec2D.Short mid){
		this.getPathList().add(this.getPathList().size()-1, mid);
	}

	/**
	 * Returns the path lists
	 * @return
	 */
	private ArrayList<Vec2D.Short> getPathList() {
		return this.pathList;
	}


}
