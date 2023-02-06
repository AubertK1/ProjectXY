package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
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

        //fixme Because of the refresh rate, sometimes the objects clip into each other before the collision is checked.
        //fixme so either we make this function detect a FUTURE collision or (as I just did temporarily) we assign
        //fixme an arbitrary "mercy range" where the objects are technically in each other by a few pixels but we don't count it.
        //fixme Here I put the "mercy range" at 5 so I removed 5 from the x range of both objects
        //puts the x values of both objects on the same horizontal plane, so I can check if they will overlap
        Line2D thisXRange = new Line2D.Float(getX() + 5, 0, getX() + getWidth() - 5, 0);
        Line2D oXRange = new Line2D.Float(o.getX() + 5, 0, o.getX() + o.getWidth() - 5, 0);

        //puts the y values of both objects on the same vertical plane, so I can check if they will overlap
        Line2D thisYRange = new Line2D.Float(0, getY() + 5, 0, getY() + getHeight() - 5);
        Line2D oYRange = new Line2D.Float(0, o.getY() + 5, 0, o.getY() + o.getHeight() - 5);

        //this midpoint is so to detect which side of the object this is on, so that it can only check for that collision
        Point thisMidpoint = new Point((int) ((thisXRange.getP2().getX()+thisXRange.getP1().getX())/2), (int) ((thisYRange.getP2().getY()+thisYRange.getP1().getY())/2));
        Point oMidpoint = new Point((int) ((oXRange.getP2().getX()+oXRange.getP1().getX())/2), (int) ((oYRange.getP2().getY()+oYRange.getP1().getY())/2));

        //check if these objects would even overlap horizontally...will be false if they would pass by each other horizontally
        if(thisXRange.intersectsLine(oXRange)){
            if(thisMidpoint.y > oMidpoint.y && getY() < o.getY() + o.getHeight()) return BOTTOMCOLLISION; //if this object is being collided from the bottom
            else if(thisMidpoint.y < oMidpoint.y && getY() + getHeight()> o.getY()) return TOPCOLLISION; //if this object is being collided from the top
        }

        //check if these objects would even overlap vertically...will be false if they would pass by each other vertically
        if(thisYRange.intersectsLine(oYRange)){
            if(thisMidpoint.x > oMidpoint.x && getX() < o.getX() + o.getWidth()) return LEFTCOLLISION; //if this object is being collided from the left
            else if(thisMidpoint.x < oMidpoint.x && getX() + getWidth() > o.getX()) return RIGHTCOLLISION; //if this object is being collided from the right
        }

        return NOCOLLISION;
    }

}
