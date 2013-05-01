package com.voracious.dragons.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.voracious.dragons.client.graphics.Drawable;
import com.voracious.dragons.client.screens.PlayScreen;
import com.voracious.dragons.client.towers.Castle;
import com.voracious.dragons.client.towers.Tower;
import com.voracious.dragons.client.units.Unit;

public class GameState implements Drawable {

    private List<Tower> towers;
    private List<Unit> units;
    private Castle p1Cast,p2Cast;
    private List<Unit> toRemove;
    private long seed;
    private Random rand;

    public GameState() {
        super();
        seed = System.nanoTime();
        rand = new Random(seed);
        towers = new ArrayList<>();
        units = new ArrayList<>();
        toRemove = new ArrayList<>();
        
        p1Cast = new Castle(true);
        this.getP1Cast().setX(0);
        this.getP1Cast().setY(PlayScreen.HEIGHT - Castle.height);
        
        p2Cast = new Castle(false);
        this.getP2Cast().setX(PlayScreen.WIDTH - Castle.width);
        this.getP2Cast().setY(0);
    }

    @Override
    synchronized
    public void draw(Graphics2D g) {
    	
    	{//draw player one's castle and health bar. @ 90% rrrrrrrrrg
    		this.getP1Cast().draw(g);
    		int leftStart= (int)(this.getP1Cast().getX());
    		int rightStart=(int)(this.getP1Cast().getX()+(this.getP1Cast().getWidth()*(this.getP1Cast().getHPRatio())));
    		int rightEnd=(int)(this.getP1Cast().getX()+this.getP1Cast().getWidth());
    		g.setColor(Color.RED);
    		g.fillRect(leftStart, (int)(this.getP1Cast().getY()+this.getP1Cast().getHeight()),(int)(rightStart-leftStart), 30);
    		if(rightEnd!=rightStart){
    			g.setColor(Color.DARK_GRAY);
    			g.fillRect(rightStart, (int)(this.getP1Cast().getY()+this.getP1Cast().getHeight()), (int)(rightEnd-rightStart), 30);
    		}
    	}
    	
    	{//draws player two's castle and health bar
    		this.getP2Cast().draw(g);
    		int leftStart=(int)(this.getP2Cast().getX());
    		int rightStart=(int)(this.getP2Cast().getX()+(this.getP2Cast().getWidth()*(this.getP2Cast().getHPRatio())));
    		int rightEnd=(int)(this.getP2Cast().getX()+this.getP2Cast().getWidth());
    		g.setColor(Color.RED);
    		g.fillRect(leftStart, (int)(this.getP2Cast().getY()-30), (int)(rightStart-leftStart), 30);
    		if(rightEnd!=rightStart){
    			g.setColor(Color.DARK_GRAY);
    			g.fillRect(rightStart, (int)(this.getP2Cast().getY()-30), (int) (rightEnd-rightStart), 30);
    		}
    	}
    	
    	
        for (Tower t : towers)
            t.draw(g);
        
        for (Unit u : units)
            u.draw(g);
    }

    synchronized
    public void tick() {
        for (Tower t : towers){
            t.tick();
            this.attackUnit(t);
        }
        
        for (Unit u : units){
            u.tick();
            if(u.getAtEnd()){
            	this.attackCastle(u);
    			toRemove.add(u);
            }
        }
        
        for(Unit u : toRemove){
            units.remove(u);
        }
        
        toRemove.clear();
    }

    synchronized
    public void addTower(Tower t) {
        towers.add(t);
    }

    synchronized
    public void removeTower(Tower t) {
        towers.remove(t);
    }

    synchronized
    public void addUnit(Unit u) {
        units.add(u);
    }

    synchronized
    public void removeUnit(Unit u) {
        units.remove(u);
    }
    
    private void attackUnit(Tower t){
    	List<Unit> tmp=new ArrayList<Unit>();
    	for(Unit u : units){
    		double xs = t.getX() - u.getX();
    		double ys = t.getY() - u.getY();
    		double dist=  Math.sqrt((xs*xs) + (ys*ys));
    		
    		if(dist <= t.getRange() && t.isPlayer1() != u.isPlayer1()){// make sure the units are the opposing player's.
    			tmp.add(u);
    		}
    	}
    	
    	if(tmp.size()!=0){
    	    int randIndex = rand.nextInt(tmp.size());
    		t.attackUnit(tmp.get(randIndex));
    		
    		if(tmp.get(randIndex).getHP() <= 0){
                toRemove.add(tmp.get(randIndex));
            }
    	}
    }
    
    private void attackCastle(Unit u){
    	if(u.isPlayer1()){
    		this.getP2Cast().setHP(this.getP2Cast().getHP()-u.getAttack());
    		System.out.println(this.getP2Cast().getHP());
    	}
    	else{
    		this.getP1Cast().setHP(this.getP1Cast().getHP()-u.getAttack());
    	}
    }
    
    public Castle getP1Cast() {
		return p1Cast;
	}

	public Castle getP2Cast() {
		return p2Cast;
	}
	
	public long getSeed(){
	    return seed;
	}
}
