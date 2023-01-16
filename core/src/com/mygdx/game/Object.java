package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public abstract class Object {

    // region properties

    private Rectangle boundingBox;

    // determins if the object should be included in a collision check
    private boolean isCollidable;
    // determins if the object should be rendered during a render pass
    private boolean isVisible;

    // endregion properties

    public Object(float posX, float posY, int sizeX, int sizeY, boolean isCollidable, boolean isVisible) {

        this.boundingBox = new Rectangle(posX, posY, sizeX, sizeY);
        this.isCollidable = isCollidable;
        this.isVisible = isVisible;
    }

    // region getters
    public float getX() {
        return boundingBox.x;
    }

    public float getY() {
        return boundingBox.y;
    }

    public float getWidth() {
        return boundingBox.width;
    }

    public float getHeight() {
        return boundingBox.height;
    }

    public boolean getIsCollidable() {
        return isCollidable;
    }

    public boolean getIsVisible() {
        return isVisible;
    }
    // endregion getters

    /**
     * checks if the this object is colliding with object o
     * 
     * @param o the second object your checking for collision with
     * @return true if the two bounding boxes overlap
     */
    public boolean isColliding(Object o) {
        // axis aligned bounding box collsion (AABBC)
        if (

        getX() < o.getX() + o.getWidth() &&
                getX() + getWidth() > o.getX() &&
                getY() < o.getY() + o.getHeight() &&
                getHeight() + getY() > o.getY()) {

            // Collision detected!
            return true;

        } else {

            // No collision
            return false;

        }
    }

}
