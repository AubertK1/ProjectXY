package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.awt.geom.Line2D;

public abstract class Object {

    // region properties
    protected Rectangle textureBounds;
    protected Rectangle hurtboxBounds;
    protected Rectangle hitboxBounds = new Rectangle(0,0,0,0);

    // hitbox side minus texture side. How far the HB's side is away from the texture's side
    private float HBLeftOffset = 0, HBRightOffset = 0, HBTopOffset = 0, HBBottomOffset = 0;

    // determins if the object should be included in a collision check
    private boolean isCollidable;
    // determins if the object should be rendered during a render pass
    private boolean isVisible;

    // visual model of the object
    protected Texture model;

    private float scale = 1;

    // endregion properties

    //region animation data
    protected DualAnimation currentAnimation; // Must declare frame type (TextureRegion)
    protected DualAnimation idleAnimation; // Must declare frame type (TextureRegion)
    protected TextureRegion modelFrame;
    protected Texture idleSheet;

    public float stateTime = 0;
    //endregion

    //fixme temporary
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    //region border conversions
    public static final int NOCOLLISION = -1;
    public static final int LEFTCOLLISION = 0;
    public static final int TOPCOLLISION = 1;
    public static final int RIGHTCOLLISION = 2;
    public static final int BOTTOMCOLLISION = 3;
    //endregion

    public Object(float posX, float posY, float sizeX, float sizeY, boolean isCollidable, boolean isVisible) {

        this.textureBounds = new Rectangle(posX, posY, sizeX, sizeY);
        this.hurtboxBounds = new Rectangle(posX, posY, sizeX, sizeY);
        this.isCollidable = isCollidable;
        this.isVisible = isVisible;
    }

    //region setters
    public void setPosition(float x, float y) {
        textureBounds.setPosition(x, y);
        hurtboxBounds.setPosition(textureBounds.x + HBLeftOffset, textureBounds.y + HBBottomOffset);
    }

    /**
     * sets the position relative to the texture's position
     * @param xOffset how far to the right it is in relation to the texture's left bound
     * @param yOffset how far to the top it is in relation to the texture's bottom bound
     */
    public void setHBPosition(float xOffset, float yOffset) {
        HBLeftOffset = xOffset;
        HBBottomOffset = yOffset;

        hurtboxBounds.setPosition(textureBounds.x + HBLeftOffset, textureBounds.y + HBBottomOffset);
    }
    public void setPositionFromHB(float hbX, float hbY){
        setPosition(hbX - HBLeftOffset, hbY - HBBottomOffset);
    }

    public void setSize(float width, float height) {
        textureBounds.setSize(width, height);
    }
    public void setHBSize(float width, float height) {
        hurtboxBounds.setSize(width, height);

        HBRightOffset = (hurtboxBounds.x + hurtboxBounds.width) - (textureBounds.x  + textureBounds.width);
        HBTopOffset = (hurtboxBounds.y + hurtboxBounds.height) - (textureBounds.y  + textureBounds.height);
    }

    public void setHurtbox(float xOffset, float yOffset, float width, float height){
        setHBPosition(xOffset, yOffset);
        setHBSize(width, height);
    }

    public void setIsCollidable(boolean isCollidable) {
        this.isCollidable = isCollidable;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void sizeToWidth(float width){
        float oldWidth = getWidth();
        float scale = width / oldWidth;
        setSize(width, getHeight() * scale);
    }

    public void scale(float scale){
        setSize(textureBounds.width * scale, textureBounds.height * scale);
        this.scale = scale;

        HBLeftOffset *= scale;
        HBRightOffset *= scale;
        HBTopOffset *= scale;
        HBBottomOffset *= scale;
        setHBPosition(HBLeftOffset, HBBottomOffset);
        setHBSize(hurtboxBounds.width * scale, hurtboxBounds.height * scale);
    }
    //endregion
    // region getters
    public float getX() {
        return textureBounds.x;
    }

    public float getY() {
        return textureBounds.y;
    }
    public float getHBX() {
        return hurtboxBounds.x;
    }

    public float getHBY() {
        return hurtboxBounds.y;
    }

    public float getWidth() {
        return textureBounds.width;
    }

    public float getHeight() {
        return textureBounds.height;
    }
    public float getHBWidth() {
        return hurtboxBounds.width;
    }
    public float getHBHeight() {
        return hurtboxBounds.height;
    }

    public boolean getIsCollidable() {
        return isCollidable;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public float getScale(){
        return scale;
    }
    // endregion getters

    public void renderHurtBox(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(Color.GREEN));
        shapeRenderer.rect(hurtboxBounds.x, hurtboxBounds.y, hurtboxBounds.width, hurtboxBounds.height);
        shapeRenderer.setColor(new Color(Color.ORANGE));
        shapeRenderer.rect(hitboxBounds.x, hitboxBounds.y, hitboxBounds.width, hitboxBounds.height);
        shapeRenderer.end();
    }
    public Animation<TextureRegion> animate(Texture animationSheet){
        return animate(animationSheet, 1, 2, .5f);
    }
    public DualAnimation animate(Texture animationSheet, int SHEET_ROWS, int SHEET_COLS, float frameDuration){
        TextureRegion[][] tmp = TextureRegion.split(animationSheet,
                animationSheet.getWidth() / SHEET_COLS, animationSheet.getHeight() / SHEET_ROWS);

        TextureRegion[] animFrames = new TextureRegion[SHEET_COLS * SHEET_ROWS];
        int index = 0;
        for (int i = 0; i < SHEET_ROWS; i++) {
            for (int j = 0; j < SHEET_COLS; j++) {
            animFrames[index++] = tmp[i][j];
            }
        }

        return new DualAnimation(frameDuration, animFrames);
    }
    public void swapAnimation(DualAnimation newAnimation){
        if(newAnimation == null || currentAnimation == newAnimation) return;

        currentAnimation = newAnimation;
        stateTime = 0;
    }

    public Rectangle applyHitbox(Rectangle hitboxBounds){
        return this.hitboxBounds = new Rectangle(getX() + (hitboxBounds.getX() * scale), getY() + (hitboxBounds.getY() * scale),
                hitboxBounds.getWidth() * scale, hitboxBounds.getHeight() * scale);
    }
    /**
     * checks if this object is colliding with object o
     *
     * @param o the second object your checking for collision with
     * @return the value of the side that the second object is touching on this object
     */
    public int isCollidingWith(Object o) {
        if(!isCollidable || !o.getIsCollidable()) return NOCOLLISION; //return if they can't collide
        // axis aligned bounding box collision (AABBC)

        //fixme Because of the refresh rate, sometimes the objects clip into each other before the collision is checked.
        //fixme so either we make this function detect a FUTURE collision or (as I just did temporarily) we assign
        //fixme an arbitrary "mercy range" where the objects are technically in each other by a few pixels but we don't count it.
        //fixme Here I put the "mercy range" at 5 so I removed 5 from the x range of both objects
        int mercyRange = 3;
        //puts the x values of both objects on the same horizontal plane, so I can check if they will overlap
        Line2D thisXRange = new Line2D.Float(getHBX() + mercyRange, 0, getHBX() + getHBWidth() - mercyRange, 0);
        Line2D oXRange = new Line2D.Float(o.getHBX() + mercyRange, 0, o.getHBX() + o.getHBWidth() - mercyRange, 0);

        //puts the y values of both objects on the same vertical plane, so I can check if they will overlap
        Line2D thisYRange = new Line2D.Float(0, getHBY() + mercyRange, 0, getHBY() + getHBHeight() - mercyRange);
        Line2D oYRange = new Line2D.Float(0, o.getHBY() + mercyRange, 0, o.getHBY() + o.getHBHeight() - mercyRange);

        //this midpoint is so to detect which side of the object this is on, so that it can only check for that collision
        Point thisMidpoint = new Point((int) ((thisXRange.getP2().getX()+thisXRange.getP1().getX())/2), (int) ((thisYRange.getP2().getY()+thisYRange.getP1().getY())/2));
        Point oMidpoint = new Point((int) ((oXRange.getP2().getX()+oXRange.getP1().getX())/2), (int) ((oYRange.getP2().getY()+oYRange.getP1().getY())/2));

        //check if these objects would even overlap horizontally...will be false if they would pass by each other horizontally
        if(thisXRange.intersectsLine(oXRange)){
            if(thisMidpoint.y > oMidpoint.y && getHBY() < o.getHBY() + o.getHBHeight()) return BOTTOMCOLLISION; //if this object is being collided from the bottom
            else if(thisMidpoint.y < oMidpoint.y && getHBY() + getHBHeight()> o.getHBY()) return TOPCOLLISION; //if this object is being collided from the top
        }

        //check if these objects would even overlap vertically...will be false if they would pass by each other vertically
        if(thisYRange.intersectsLine(oYRange)){
            if(thisMidpoint.x > oMidpoint.x && getHBX() < o.getHBX() + o.getHBWidth()) return LEFTCOLLISION; //if this object is being collided from the left
            else if(thisMidpoint.x < oMidpoint.x && getHBX() + getHBWidth() > o.getHBX()) return RIGHTCOLLISION; //if this object is being collided from the right
        }

        return NOCOLLISION;
    }
/*
    public static int isColliding(Rectangle r1, Rectangle r2) {
        //fixme Because of the refresh rate, sometimes the objects clip into each other before the collision is checked.
        //fixme so either we make this function detect a FUTURE collision or (as I just did temporarily) we assign
        //fixme an arbitrary "mercy range" where the objects are technically in each other by a few pixels but we don't count it.
        //fixme Here I put the "mercy range" at 5 so I removed 5 from the x range of both objects
        int mercyRange = 3;
        //puts the x values of both objects on the same horizontal plane, so I can check if they will overlap
        Line2D thisXRange = new Line2D.Float(r1.getX() + mercyRange, 0, r1.getX() + r1.getWidth() - mercyRange, 0);
        Line2D oXRange = new Line2D.Float(r2.getX() + mercyRange, 0, r2.getX() + r2.getWidth() - mercyRange, 0);

        //puts the y values of both objects on the same vertical plane, so I can check if they will overlap
        Line2D thisYRange = new Line2D.Float(0, getHBY() + mercyRange, 0, getHBY() + getHBHeight() - mercyRange);
        Line2D oYRange = new Line2D.Float(0, o.getHBY() + mercyRange, 0, o.getHBY() + o.getHBHeight() - mercyRange);

        //this midpoint is so to detect which side of the object this is on, so that it can only check for that collision
        Point thisMidpoint = new Point((int) ((thisXRange.getP2().getX()+thisXRange.getP1().getX())/2), (int) ((thisYRange.getP2().getY()+thisYRange.getP1().getY())/2));
        Point oMidpoint = new Point((int) ((oXRange.getP2().getX()+oXRange.getP1().getX())/2), (int) ((oYRange.getP2().getY()+oYRange.getP1().getY())/2));

        //check if these objects would even overlap horizontally...will be false if they would pass by each other horizontally
        if(thisXRange.intersectsLine(oXRange)){
            if(thisMidpoint.y > oMidpoint.y && getHBY() < o.getHBY() + o.getHBHeight()) return BOTTOMCOLLISION; //if this object is being collided from the bottom
            else if(thisMidpoint.y < oMidpoint.y && getHBY() + getHBHeight()> o.getHBY()) return TOPCOLLISION; //if this object is being collided from the top
        }

        //check if these objects would even overlap vertically...will be false if they would pass by each other vertically
        if(thisYRange.intersectsLine(oYRange)){
            if(thisMidpoint.x > oMidpoint.x && getHBX() < o.getHBX() + o.getHBWidth()) return LEFTCOLLISION; //if this object is being collided from the left
            else if(thisMidpoint.x < oMidpoint.x && getHBX() + getHBWidth() > o.getHBX()) return RIGHTCOLLISION; //if this object is being collided from the right
        }

        return NOCOLLISION;
    }
*/

}
