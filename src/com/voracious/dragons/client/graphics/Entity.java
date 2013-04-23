package com.voracious.dragons.client.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.voracious.dragons.common.Vec2D;

public class Entity implements Drawable {

    private double x, y;
    private double dx, dy;
    private int width, height;
    private ArrayList<Animation> animations;
    private int currentAnimation;

    /**
     * Construct a new Entity object
     * 
     * @param filename path to the sprite sheet
     * @param numFrames number of frames in each animation row in the sprite sheet
     * @param width width of each frame
     * @param height height of each frame
     */
    public Entity(String filename, int[] numFrames, int width, int height) {
        animations = new ArrayList<Animation>();
        
        BufferedImage image;
        try {
            image = ImageIO.read(Entity.class.getResourceAsStream(filename));
            
            for(int i=0; i<numFrames.length; i++){
                animations.add(new Animation(image.getSubimage(0, i*height, image.getWidth(), height), 
                                             numFrames[i], width, height));
            }
        } catch (IOException e) {
            Logger.getLogger(Entity.class).error("Failed to load image", e);
        }
        
        this.setWidth(width);
        this.setHeight(height);
        
        x = y = 0;
        dx = dy = 0;
    }

    @Override
    public void draw(Graphics2D g) {
        animations.get(currentAnimation).draw(g, (int) x, (int) y);
    }

    public void tick() {
        x += dx;
        y += dy;
        
        animations.get(currentAnimation).tick();
    }
    
    public void play(){
        animations.get(currentAnimation).play();
    }
    
    public void pause() {
        animations.get(currentAnimation).pause();
    }
    
    public void restart() {
        animations.get(currentAnimation).restart();
    }
    
    public void setCurrentAnimation(int animation){
        animations.get(currentAnimation).pause();
        animations.get(currentAnimation).restart();
        
        currentAnimation = animation % animations.size();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public void setPos(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void setVelocity(Vec2D v) {
        this.dx = v.x;
        this.dy = v.y;
    }

    public Vec2D getVelocity() {
        return new Vec2D.Double(dx, dy);
    }

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void nextFrame() {
		animations.get(currentAnimation).nextFrame();
	}
}
