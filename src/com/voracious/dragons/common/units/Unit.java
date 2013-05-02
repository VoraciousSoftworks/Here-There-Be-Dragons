package com.voracious.dragons.common.units;

import java.util.ArrayList;
import java.util.List;

import com.voracious.dragons.client.graphics.Entity;
import com.voracious.dragons.common.Vec2D;

public abstract class Unit extends Entity {

    public  double speed;
    private List<Vec2D.Short> path;
    private short goingTo;
    private boolean atEnd = false;
    private int HP;
    private int attack;
    private boolean isPlayer1;

    public Unit(String filename, int[] numFrames, int width, int height, List<Vec2D.Short> path, boolean whos,int hp, int attk,double spd) {
        super(filename, numFrames, width, height);
        this.path = path;
        this.goingTo = 0;
        Vec2D.Short init = path.get(0);
        this.setPos(init);
        
        speed=spd;
        
        this.HP=hp;
        this.attack=attk;
        
        this.isPlayer1 = whos;

        this.toNextNode();
    }
    
    public static Unit makeUnit(int id, List<Vec2D.Short> path, boolean whos){
        Unit result = null;
        
        switch(id){
        case Dragon.ID:
            result = new Dragon(path, whos);
            break;
        case Swordsman.ID:
        	result = new Swordsman(path,whos);
        	break;
        case BatteringRam.ID:
        	result = new BatteringRam(path,whos);
        	break;
        }
        
        return result;
    }
    
    public static Unit makeUnit(String unitStr){
        String[] us = unitStr.split("|");
        Unit result = null;
        
        String[] nodes = us[8].split("+");
        List<Vec2D.Short> path = new ArrayList<>(nodes.length); 
        
        switch(Integer.parseInt(us[0])){
        case Dragon.ID:
            result = new Dragon(path, Integer.parseInt(us[1]) == 1);
            break;
        }
        
        result.setHP(Integer.parseInt(us[2]));
        result.setX(Double.parseDouble(us[3]));
        result.setY(Double.parseDouble(us[4]));
        result.setVelocity(new Vec2D.Double(Double.parseDouble(us[5]), Double.parseDouble(us[6])));
        result.goingTo = Short.parseShort(us[7]);
        
        return result;
    }
    
    public String toString(){
        String result = this.getUnitId() + "|";
        result += (isPlayer1 ? 1 : 0) + "|";
        result += this.getHP();
        result += this.getX() + "|";
        result += this.getY() + "|";
        Vec2D.Double v = (Vec2D.Double) this.getVelocity();
        result += v.x + "|" + v.y + "|";
        result += this.goingTo + "|";
        
        for(Vec2D.Short node : path){
            result += node.x + "+" + node.y + "+";
        }
        
        return result;
    }

    @Override
    public void tick(){
        super.tick();
        
        if(!atEnd){
            Vec2D.Short goingToPos = path.get(goingTo);
            Vec2D.Double vel = (Vec2D.Double) this.getVelocity();
            
            if(vel.x > 0){
                if(this.getX() > goingToPos.x){
                    if(vel.y > 0){
                        if(this.getY() > goingToPos.y){
                            toNextNode();
                        }
                    }else{
                        if(this.getY() < goingToPos.y){
                            toNextNode();
                        }
                    }
                }
            }else{
                if(this.getX() < goingToPos.x){
                    if(vel.y > 0){
                        if(this.getY() > goingToPos.y){
                            toNextNode();
                        }
                    }else{
                        if(this.getY() < goingToPos.y){
                            toNextNode();
                        }
                    }
                } 
            }
        }
    }

    private void toNextNode() {
        goingTo++;
        if (path.size() > goingTo) {
            Vec2D.Double next = new Vec2D.Double(path.get(goingTo));
            next.sub(new Vec2D.Double(getX(), getY()));
            next.div(Math.sqrt(next.dot(next)));
            next.mult(this.speed);
            this.setVelocity(next);
        } else {
            atEnd = true;
            this.setVelocity(new Vec2D.Double(0, 0));
        }
    }

    public abstract byte getUnitId();

    public int getHP() {
        return HP;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    public int getAttack() {
        return attack;
    }

    public boolean getAtEnd() {
        return atEnd;
    }

    public boolean isPlayer1() {
        return isPlayer1;
    }
}
