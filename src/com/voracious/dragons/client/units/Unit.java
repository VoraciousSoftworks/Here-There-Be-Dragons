package com.voracious.dragons.client.units;

import java.util.List;

import com.voracious.dragons.client.graphics.Entity;
import com.voracious.dragons.common.Vec2D;

public abstract class Unit extends Entity {
	
    public static double default_velocity = 3.0;
    private List<Vec2D.Short> path;
    private short goingTo;
    private boolean atEnd = false;
    
    public Unit(String filename, int[] numFrames, int width, int height, List<Vec2D.Short> path) {
        super(filename, numFrames, width, height);
        this.path = path;
        this.goingTo = 1;
        Vec2D.Short init = path.get(0);
        Vec2D.Short next = new Vec2D.Short(path.get(1));
        this.setPos(init);
        
        next.div(Math.sqrt(next.dot(next)));
        next.y = -next.y;
        next.mult(default_velocity);
        this.setVelocity(next);
    }
    
    @Override
    public void tick(){
        super.tick();
        
        if(!atEnd){
            Vec2D.Short goingToPos = path.get(goingTo);
            Vec2D vel = this.getVelocity();
            
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
    
    public void toNextNode(){
        goingTo++;
        if(path.size() > goingTo){
            Vec2D.Short next = new Vec2D.Short(path.get(goingTo));
            next.div(Math.sqrt(next.dot(next)));
            next.y = -next.y;
            next.mult(default_velocity);
            this.setVelocity(next);
        }else{
            atEnd = true;
            this.setVelocity(new Vec2D.Double(0, 0));
        }
    }
    
    public abstract byte getUnitId();
}
