package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;
import java.awt.geom.Line2D;

public abstract class Object {

    // region properties
    protected Rectangle textureBounds;
    protected Rectangle hurtboxBounds;
    protected Rectangle hitbox = new Rectangle(0,0,0,0);
    protected Point hitboxFocalPoint = new Point(-1, -1);

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
    public static final int NOCOLLISION = 0;
    public static final int LEFTCOLLISION = 1;
    public static final int TOPCOLLISION = 2;
    public static final int RIGHTCOLLISION = 3;
    public static final int BOTTOMCOLLISION = 4;
    //endregion
    //region direction conversions
    public static final int NODIRECTION = -1;
    public static final int LEFT = 0;
    public static final int UPLEFT = 1;
    public static final int UP = 2;
    public static final int UPRIGHT = 3;
    public static final int RIGHT = 4;
    public static final int DOWNRIGHT = 5;
    public static final int DOWN = 6;
    public static final int DOWNLEFT = 7;
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

    public Rectangle getHurtboxBounds(){
        return hurtboxBounds;
    }
    public Rectangle getHitboxBounds(){
        return hitbox;
    }
    // endregion getters

    public void renderOutlines(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(new Color(Color.PINK));
//        shapeRenderer.rect(textureBounds.x, textureBounds.y, textureBounds.width, textureBounds.height);
        shapeRenderer.setColor(new Color(Color.GREEN));
        shapeRenderer.rect(hurtboxBounds.x, hurtboxBounds.y, hurtboxBounds.width, hurtboxBounds.height);
        shapeRenderer.setColor(new Color(Color.ORANGE));
        shapeRenderer.rect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        if(hitboxFocalPoint.x != -1) {
            shapeRenderer.setColor(new Color(Color.RED));
            shapeRenderer.rect((float) hitboxFocalPoint.getX()-2, (float) hitboxFocalPoint.getY()-2, 4, 4);
        }
        shapeRenderer.end();
    }
    public DualAnimation animate(Texture animationSheet, int SHEET_ROWS, int SHEET_COLS, int totalFrames){
        TextureRegion[][] tmp = TextureRegion.split(animationSheet,
                animationSheet.getWidth() / SHEET_COLS, animationSheet.getHeight() / SHEET_ROWS);

        TextureRegion[] animFrames = new TextureRegion[SHEET_COLS * SHEET_ROWS];
        int index = 0;
        for (int i = 0; i < SHEET_ROWS; i++) {
            for (int j = 0; j < SHEET_COLS; j++) {
            animFrames[index++] = tmp[i][j];
            }
        }

        return new DualAnimation(totalFrames, animFrames);
    }
    public void swapAnimation(DualAnimation newAnimation){
        swapAnimation(newAnimation, false);
    }
    public void swapAnimation(DualAnimation newAnimation, boolean forceReset){
        if(!forceReset && (newAnimation == null || currentAnimation == newAnimation)) return;

        currentAnimation = newAnimation;
        stateTime = 0;
    }

    public void applyHitbox(Rectangle hitboxBounds, boolean flip){
        this.hitbox.set(getX() + ((flip ? (getWidth() / scale) - hitboxBounds.getX() - hitboxBounds.getWidth() : hitboxBounds.getX()) * scale),
                getY() + (hitboxBounds.getY() * scale),
                hitboxBounds.getWidth() * scale, hitboxBounds.getHeight() * scale);
    }
    public void applyFocalPoint(Point focalPoint, boolean flip){
        if(focalPoint.getX() == -1 && focalPoint.getY() == -1) {
            this.hitboxFocalPoint.setLocation(-1, -1);
            return;
        }
        this.hitboxFocalPoint.setLocation(
                getX() + ((flip ? (getWidth() / scale) - focalPoint.getX(): focalPoint.getX()) * scale),
                getY() + (focalPoint.getY() * scale)
        );
    }

    /**
     * checks if this object is colliding with object o
     *
     * @param o the second object your checking for collision with
     * @return the value of the side that the second object is touching on this object
     */
    public boolean[] isCollidingWith(Object o) {
        if(!isCollidable || !o.getIsCollidable()) return new boolean[]{true, false, false, false, false}; //return if they can't collide

        return isColliding(this, o);
    }

    public static boolean[] isColliding(Object o1, Object o2){
        if(!o1.getIsCollidable() || !o2.getIsCollidable()) return new boolean[]{true, false, false, false, false}; //return if they can't collide

        return isColliding(o1.getHurtboxBounds(), o2.getHurtboxBounds());
    }
    public static boolean[] isColliding(Rectangle r1, Rectangle r2) {
        //fixme Because of the refresh rate, sometimes the objects clip into each other before the collision is checked.
        //fixme so either we make this function detect a FUTURE collision or (as I just did temporarily) we assign
        //fixme an arbitrary "mercy range" where the objects are technically in each other by a few pixels but we don't count it.
        //fixme Here I put the "mercy range" at 3 so I removed 3 from the x range of both objects
        int mercyRange = 3;
        boolean[] collisions = new boolean[]{true, false, false, false, false};

        //puts the x values of both objects on the same horizontal plane, so I can check if they will overlap
        Line2D thisXRange = new Line2D.Float(r1.getX() + mercyRange, 0, r1.getX() + r1.getWidth() - mercyRange, 0);
        Line2D oXRange = new Line2D.Float(r2.getX() + mercyRange, 0, r2.getX() + r2.getWidth() - mercyRange, 0);

        //puts the y values of both objects on the same vertical plane, so I can check if they will overlap
        Line2D thisYRange = new Line2D.Float(0, r1.getY() + mercyRange, 0, r1.getY() + r1.getHeight() - mercyRange);
        Line2D oYRange = new Line2D.Float(0, r2.getY() + mercyRange, 0, r2.getY() + r2.getHeight() - mercyRange);

        //this midpoint is so to detect which side of the object this is on, so that it can only check for that collision
        Point thisMidpoint = new Point((int) ((thisXRange.getP2().getX()+thisXRange.getP1().getX())/2), (int) ((thisYRange.getP2().getY()+thisYRange.getP1().getY())/2));
        Point oMidpoint = new Point((int) ((oXRange.getP2().getX()+oXRange.getP1().getX())/2), (int) ((oYRange.getP2().getY()+oYRange.getP1().getY())/2));

        //check if these objects would even overlap horizontally...will be false if they would pass by each other horizontally
        if(thisXRange.intersectsLine(oXRange)){
            if(thisMidpoint.y > oMidpoint.y && r1.getY() < r2.getY() + r2.getHeight()) collisions[BOTTOMCOLLISION] = true; //if this object is being collided from the bottom
            else if(thisMidpoint.y < oMidpoint.y && r1.getY() + r1.getHeight()> r2.getY()) collisions[TOPCOLLISION] = true; //if this object is being collided from the top
        }

        //check if these objects would even overlap vertically...will be false if they would pass by each other vertically
        if(thisYRange.intersectsLine(oYRange)){
            if(thisMidpoint.x > oMidpoint.x && r1.getX() < r2.getX() + r2.getWidth()) collisions[LEFTCOLLISION] = true; //if this object is being collided from the left
            else if(thisMidpoint.x < oMidpoint.x && r1.getX() + r1.getWidth() > r2.getX()) collisions[RIGHTCOLLISION] = true; //if this object is being collided from the right
        }

        for (int i = 0; i < collisions.length; i++) { //if another collision was found, make the NOCOLLISION element false
            if (i == NOCOLLISION) continue; //skips the value of the NOCOLLISION element bc it will be true by default
            if (collisions[i]) {
                collisions[NOCOLLISION] = false;
                break;
            }
        }
        return collisions;
    }

    public void pushOutOf(Object o, int direction){
        pushOut(this, o, direction);
    }
    /**
     *
     * @param o1 The object that will be moved out
     * @param o2 The object that will not be moved
     * @param direction The direction o1 will be moved out of o2
     */
    public static void pushOut(Object o1, Object o2, int direction){
        int mercyRange = 3; //MUST BE THE SAME AS IN isColliding()
        switch (direction){
            case LEFT:
                float leftX = o2.getHBX() - o1.getHBWidth() + mercyRange;
                o1.setPositionFromHB(leftX, o1.getY());
                break;
            case RIGHT:
                float rightX = o2.getHBX() + o2.getHBWidth() - mercyRange;
                o1.setPositionFromHB(rightX, o1.getY());
                break;
            case DOWN:
                float bottomY = o2.getHBY() - o1.getHBHeight();
                o1.setPosition(o1.getX(), bottomY);
                break;
            case UP:
                float topY = o2.getHBY() + o2.getHBHeight() - mercyRange;
                o1.setPosition(o1.getX(), topY);
                break;
        }
    }
}
