package com.mygdx.game;

public abstract class Object {
    
    //region properties 

    // the cordinates of the bottom left corner of the objects bounding box
    private int posX;
    private int posY;

    // the dimesions of the box in the x and y axies
    private int sizeX;
    private int sizeY;

    // determins if the object should be included in a collision check
    private boolean isCollidable;
    // determins if the object should be rendered during a render pass 
    private boolean isVisible;

    //endregion properties

    public Object(int posX, int posY, int sizeX, int sizeY, boolean isCollidable, boolean isVisible) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.isCollidable = isCollidable;
        this.isVisible = isVisible;
    }

    public Object() {
        this.posX = 0;
        this.posY = 0;
        this.sizeX = 10;
        this.sizeY = 10;
        this.isCollidable = false;
        this.isVisible = false;   
    }

    //region getters
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public boolean getIsCollidable() {
        return isCollidable;
    }

    public boolean getIsVisible() {
        return isCollidable;
    }
    //endregion getters

    /**
     * checks if the this object is colliding with object o
     * @param o the second object your checking for collision with
     * @return true if the two bounding boxes overlap
     */
    public boolean isColliding(Object o) {
        // axis aligned bounding box collsion (AABBC)
        if (
            posX < o.posX + o.sizeX &&
            posX + sizeX > o.posX &&
            posY < o.posY + o.sizeY &&
            sizeY + posY > o.posY
        ) {
            // Collision detected!
            return true;
        } else {
            // No collision
            return false;
        }
    }


}
