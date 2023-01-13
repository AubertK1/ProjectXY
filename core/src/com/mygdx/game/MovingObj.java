package com.mygdx.game;

public abstract class MovingObj extends Object {
    


    public MovingObj(int posX, int posY, int sizeX, int sizeY, boolean isCollidable, boolean isVisible) {
        super(posX, posY, sizeX, sizeY, isCollidable, isVisible);
    }
}
