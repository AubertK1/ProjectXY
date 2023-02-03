package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

import java.awt.geom.Line2D;

public abstract class Object {

    // region properties
    protected Rectangle bounds;

    // determins if the object should be included in a collision check
    private boolean isCollidable;
    // determins if the object should be rendered during a render pass
    private boolean isVisible;

    //region border conversions
    public static final int NOCOLLISION = -1;
    public static final int LEFTCOLLISION = 0;
    public static final int TOPCOLLISION = 1;
    public static final int RIGHTCOLLISION = 2;
    public static final int BOTTOMCOLLISION = 3;
    //endregion
    // endregion properties

    public Object(float posX, float posY, float sizeX, float sizeY, boolean isCollidable, boolean isVisible) {

        this.bounds = new Rectangle(posX, posY, sizeX, sizeY);
        this.isCollidable = isCollidable;
        this.isVisible = isVisible;
    }

    //region setters
    public void setPosition(float x, float y) {
        bounds.setPosition(x, y);
    }

    public void setSize(float width, float height) {
        bounds.setSize(width, height);
    }

    public void setIsCollidable(boolean isCollidable) {
        this.isCollidable = isCollidable;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
    //endregion
    // region getters
    public float getX() {
        return bounds.x;
    }

    public float getY() {
        return bounds.y;
    }

    public float getWidth() {
        return bounds.width;
    }

    public float getHeight() {
        return bounds.height;
    }

    public boolean getIsCollidable() {
        return isCollidable;
    }

    public boolean getIsVisible() {
        return isVisible;
    }
    // endregion getters

    /**
     * checks if this object is colliding with object o
     * 
     * @param o the second object your checking for collision with
     * @return the value of the side that the second object is touching on this object
     */
    public int isColliding(Object o) {
        // axis aligned bounding box collision (AABBC)

        //puts the x values of both objects on the same vertical plane, so I can check if they will overlap
        Line2D thisXValueInterval = new Line2D.Float(getX(), 0, getX() + getWidth(), 0);
        Line2D oXValueInterval = new Line2D.Float(o.getX(), 0, o.getX() + o.getWidth(), 0);

        //if these objects would even overlap vertically...will be false if they would pass by each other vertically
        if(thisXValueInterval.intersectsLine(oXValueInterval)){
            if(getY() < o.getY() + o.getHeight()) return BOTTOMCOLLISION; //if this object is being collided from the bottom
            else if(getY() + getHeight()> o.getY()) return TOPCOLLISION; //if this object is being collided from the top
        }

        //puts the y values of both objects on the same horizontal plane, so I can check if they will overlap
        Line2D thisYValueInterval = new Line2D.Float(0, getY(), 0, getY() + getHeight());
        Line2D oYValueInterval = new Line2D.Float(0, o.getY(), 0, o.getY() + o.getHeight());

        //if these objects would even overlap horizontally...will be false if they would pass by each other horizontally
        if(thisYValueInterval.intersectsLine(oYValueInterval)){
            if(getX() < o.getX() + o.getWidth()) return LEFTCOLLISION; //if this object is being collided from the left
            else if(getX() + getWidth() > o.getX()) return RIGHTCOLLISION; //if this object is being collided from the right
        }

        return NOCOLLISION;
    }

}
