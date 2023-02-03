package com.mygdx.game;

public abstract class MovingObj extends Object {

    

    /**
     * 
     * @param posX
     * @param posY
     * @param sizeX
     * @param sizeY
     * @param isCollidable
     * @param isVisible
     */
    public MovingObj(int posX, int posY, int sizeX, int sizeY, boolean isCollidable, boolean isVisible) {
        super(posX, posY, sizeX, sizeY, isCollidable, isVisible);
    }
}
