package com.voracious.dragons.common;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import com.voracious.dragons.client.graphics.Drawable;
import com.voracious.dragons.client.screens.PlayScreen;
import com.voracious.dragons.client.towers.Castle;
import com.voracious.dragons.client.towers.Tower;
import com.voracious.dragons.client.units.Unit;

public class GameState implements Drawable {

    private List<Tower> towers;
    private List<Unit> units;
    private Castle p1Cast,p2Cast;
    private boolean isplayer1;
    

    public GameState(boolean player1) {
        super();
        isplayer1=player1;
        towers=new ArrayList<>();
        units=new ArrayList<>();
        
        this.setP1Cast(new Castle(true));
        this.getP1Cast().setX(0);
        this.getP1Cast().setY(PlayScreen.HEIGHT - Castle.height);
        
        this.setP2Cast(new Castle(false));
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
        	this.attack_an_Unit(t);
            t.tick();
        }
        
        for (Unit u : units){
            u.tick();
            if(u.getAtEnd()){//hit test needed?
            	this.unit_attack_Castle(u);
    			this.units.remove(u);
            }
        }
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
    
    public void attack_an_Unit(Tower t){
    	ArrayList<Unit> tmp=new ArrayList<Unit>();
    	for(Unit u:units){
    		double xs=t.getX()-u.getX();
    		double ys=t.getY()-u.getY();
    		double dist=Math.sqrt((xs*xs)+(ys*ys));
    		if(dist<=t.getRange()&&this.getISPLAYER1()!=u.getISPLAYER1()){// make sure the units are the opposing player's.
    			tmp.add(u);
    		}
    	}
    	int randLoc=(int) (Math.random()*(tmp.size()));
    	if(tmp.size()!=0){
    		//System.out.print(tmp.get(randLoc).getHP());
    		t.attackUnit(tmp.get(randLoc));
    		//System.out.print(", "+tmp.get(randLoc).getHP()+"\n");
    		if(tmp.get(randLoc).getHP()<=0){
    			this.units.remove(tmp.get(randLoc));
    		}
    			
    	}
    }
    
    public void unit_attack_Castle(Unit u){
    	if(u.isPlayer1()){
    		this.getP2Cast().setCHP(this.getP2Cast().getCHP()-u.getAttack());
    		System.out.println(this.getP2Cast().getCHP());
    	}
    	else{
    		this.getP1Cast().setCHP(this.getP1Cast().getCHP()-u.getAttack());
    	}
    }
    
    public Castle getP1Cast() {
		return p1Cast;
	}

	public void setP1Cast(Castle p1Cast) {
		this.p1Cast = p1Cast;
	}

	public Castle getP2Cast() {
		return p2Cast;
	}

	public void setP2Cast(Castle p2Cast) {
		this.p2Cast = p2Cast;
	}
	
	public boolean getISPLAYER1(){
		return this.isplayer1;
	}
}
