package com.voracious.dragons.client.units;

import java.util.List;

import com.voracious.dragons.client.graphics.Entity;
import com.voracious.dragons.common.Vec2D;

public abstract class Unit extends Entity {

    public static double default_velocity = 3.0;
    private List<Vec2D.Short> path;
    private short goingTo;
    private boolean atEnd = false;
    private int HP = 100;
    private int attack = 5;
    private boolean isPlayer1;

    public Unit(String filename, int[] numFrames, int width, int height, List<Vec2D.Short> path, boolean whos) {
        super(filename, numFrames, width, height);
        this.path = path;
        this.goingTo = 0;
        Vec2D.Short init = path.get(0);
        this.setPos(init);

        this.isPlayer1 = whos;

        this.toNextNode();
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
            next.mult(default_velocity);
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
