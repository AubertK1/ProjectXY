package com.mygdx.game;

public abstract class MovingObj extends Object {
    //object's velocity
    protected float horVelocity = 0;
    protected float vertVelocity = 0;
    protected boolean canFall = true; //can the object fall lower

    /**
     * 
     * @param posX
     * @param posY
     * @param sizeX
     * @param sizeY
     * @param isCollidable
     * @param isVisible
     */
    public MovingObj(float posX, float posY, float sizeX, float sizeY, boolean isCollidable, boolean isVisible) {
        super(posX, posY, sizeX, sizeY, isCollidable, isVisible);
    }

    public void applyPhysics(){
        float frameRate = Main.getFrameRate();

        if (canFall) {
            vertVelocity += GameScreen.GRAVITY;
            if (vertVelocity < -1000) vertVelocity = -1000; //maximum downward velocity
        }
        else if (vertVelocity < 0) vertVelocity = 0;
        setPosition(getX(), getY() + (frameRate * vertVelocity));
        setPosition(getX() + (frameRate * horVelocity), getY());
        slowDown();
    }

    public void slowDown(){
        if(horVelocity > 0){ //if moving right...
            horVelocity -= 20;
            if(horVelocity < 0) stop();
        }
        else if(horVelocity < 0){ //if moving left...
            horVelocity += 20;
            if(horVelocity > 0) stop();
        }
    }
    public void stop(){
        horVelocity = 0;
    }

    public float getXVelocity() {
        return horVelocity;
    }
    public float getYVelocity() {
        return vertVelocity;
    }
}
