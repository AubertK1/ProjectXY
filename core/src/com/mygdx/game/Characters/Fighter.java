package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.*;
import com.mygdx.game.OI.Player;

import java.awt.*;

/**
 * All fighters will extend from this class. It declares the basic properties
 * every fighter will have
 * (speed, health, damage, etc.) which may be changed in the fighter's separate
 * class
 */
public class Fighter extends MovingObj{

    // region properties

    // the fighter will be assigned to a player when chosen
    protected Player player;

    //region stats
    protected static int maxJumps = 3;
    protected float speed = 500;
    protected int damage = 10;
    protected int maxHealth = 100;
    protected int health = maxHealth;
    protected int fortitude = 15;
    //endregion

    //region states of being
    protected boolean isAlive = true; //is the player alive
    protected boolean canFall = true; //can the player fall lower
    protected boolean isJumping = false; //is the player jumping
    protected boolean isBlocking = false; //is the player blocking
    protected boolean isFacingRight = true;

    protected boolean isInHitStun = false;
    //endregion

    protected int jumpsLeft = maxJumps;
    //the next frame they'll be able to jump at
    protected int nextJumpFrame = 0;
    protected int nextUnstunFrame = 0;

    // endregion properties

    //region animations
    DualAnimation runAnimation;
    DualAnimation jumpAnimation;
    DualAnimation fallAnimation;
    //attacks
    DualAnimation nLightAnimation;
    DualAnimation sLightAnimation;
    //endregion

    //region attacks
    public enum Attack {
        NOATTACK,
        NLIGHT, SLIGHT, DLIGHT
    }
    protected Attack currentATK = Attack.NOATTACK;
    protected boolean attackAlreadyHit = false;
    //endregion

    public Fighter(float x, float y, float width, float height, boolean isCollidable, boolean isVisible, Player player) {
        super(x, y, width, height, isCollidable, isVisible);
        this.player = player;
    }

    public void update() {
        float deltaTime = Main.getFrameRate();

        //region gravity
        if(!isInHitStun) {
            if (canFall) {
                vertVelocity += GameScreen.GRAVITY;
                if(vertVelocity < 0 && !isAttacking()) swapAnimation(fallAnimation);
                if (vertVelocity < -1000) vertVelocity = -1000; //maximum downward velocity
            }
            else if (vertVelocity < 0) vertVelocity = 0;
        }
        setPosition(getX(), getY() + (deltaTime * vertVelocity));
        setPosition(getX() + (deltaTime * horVelocity), getY());
        slowDown();
        //endregion

        //region collision
        if(this.isCollidingWith(Main.gameScreen.platform) == BOTTOMCOLLISION){ //if touching a platform
            canFall = false;
            stopJump();
            resetJumps();
        } else {
            canFall = true;

            if(isJumping && this.isCollidingWith(Main.gameScreen.platform) == TOPCOLLISION){
                stopJump();
                vertVelocity = 0;
            }
        }

        if(this.isCollidingWith(Main.gameScreen.platform) == LEFTCOLLISION || this.isCollidingWith(Main.gameScreen.platform) == RIGHTCOLLISION){
            stopJump();
            resetJumps();
            vertVelocity = -135;
        }
        //endregion

        //applying a jump cool down
        if (GameScreen.getFrame() >= nextJumpFrame) {
            isJumping = false;
        }
        if(currentATK == Attack.NOATTACK) attackAlreadyHit = false;
        if(GameScreen.getFrame() >= nextUnstunFrame) isInHitStun = false;
    }

    /**
     * These will be extended and based on the fighter
     */
    // region attacks

    // region light attacks
    public void upLightAtk() {

    }

    public void neutralLightAtk() {

    }

    public void sideLightAtk() {

    }

    public void downLightAtk() {
         
    }
    // endregion

    // region heavy attacks
    public void upHeavyAtk() {
         
    }

    public void neutralHeavyAtk() {
         
    }

    public void sideHeavyAtk() {
         
    }

    public void downHeavyAtk() {
         
    }
    // endregion

    // region air attacks
    public void upAirAtk() {
    }

    public void neutralAirAtk() {
    }

    public void sideAirAtk() {
    }

    public void downAirAtk() {
    }

    public void recoveryAtk() {
    }
    // endregion

    // endregion

    //region movement
    public void moveLeft(){
        horVelocity = -speed;
        isFacingRight = false;

        if(runAnimation != null && currentATK == Attack.NOATTACK && vertVelocity == 0) swapAnimation(runAnimation);
    }
    public void moveRight(){
        horVelocity = speed;
        isFacingRight = true;

        if(runAnimation != null && currentATK == Attack.NOATTACK && vertVelocity == 0) swapAnimation(runAnimation);
    }
    public void moveDown(){
        setPosition(getX(), getY() - Main.getFrameRate() * (speed / 2f));
    }

    public void jump(){
        if(jumpsLeft > 0) {
            stopJump();
            isJumping = true;
            canFall = true;
            vertVelocity = 1660;
            nextJumpFrame = GameScreen.getFrame() + 6;
            jumpsLeft--;

            if(jumpAnimation != null) swapAnimation(jumpAnimation, true);
        }
    }
    public void stopJump(){
        isJumping = false;
    }
    public void resetJumps(){
        jumpsLeft = maxJumps;
    }

    public void pullTo(Point point, float time){
        if(point.x == -1) return;
        float deltaX = point.x - (hurtboxBounds.x + (hurtboxBounds.width / 2f));
        float deltaY = point.y - (hurtboxBounds.y + (hurtboxBounds.height / 2f));

        horVelocity = deltaX / time;
        vertVelocity = deltaY / time;
    }
    //endregion

    public void block() {
        isBlocking = true;
    }

    public void takeDamage(int damage){
        health -= damage;
    }
    public void knockBack(int direction, float multiplier, boolean preferRight){
        float baseHorKB = 1120;
        float baseVertKB = -GameScreen.GRAVITY + baseHorKB;

        canFall = true;
        switch (direction){
            case LEFTCOLLISION:
                horVelocity = -baseHorKB * multiplier * .74f;
                vertVelocity = baseVertKB * multiplier * .9f;
                break;
            case RIGHTCOLLISION:
                horVelocity = baseHorKB * multiplier * .74f;
                vertVelocity = baseVertKB * multiplier * 1;
                break;
            case TOPCOLLISION:
                horVelocity = (preferRight ? baseHorKB : -baseHorKB) * multiplier * .35f;
                vertVelocity = baseVertKB * multiplier;
                break;
            case BOTTOMCOLLISION:
                horVelocity = baseHorKB * multiplier * .35f;
                vertVelocity = -baseVertKB * multiplier;
                break;
        }
    }
    public void stun(int duration){
        isInHitStun = true;
        nextUnstunFrame = GameScreen.getFrame() + duration;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public boolean canFall() {
        return canFall;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public int getHealth(){
        return health;
    }

    public int getFortitude() {
        return fortitude;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isAttacking(){
        return !(currentATK == Attack.NOATTACK);
    }

    public void reset(){
        health = maxHealth;

        horVelocity = 0;
        vertVelocity = 0;

        resetJumps();

        setPosition(GameScreen.spawnCenter.x, GameScreen.spawnCenter.y);
    }

    public Attack getCurrentATK(){
        return currentATK;
    }

    /**
     * assigns this fighter to a player so that it can get its bounds updated
     * 
     * @param player the player this fighter belongs to
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * renders the fighter's model onto the screen
     * @param batch just put batch
     */
    public void render(SpriteBatch batch) {
        player.update();

        //this draws the fighter flipped depending on which way it is facing
        boolean flip = !isFacingRight();
        if(currentAnimation == null) { //if no animation...
            //this draws the fighter flipped depending on which way it is facing
            batch.draw(model, flip ? getX() + getWidth() : getX(), getY(), flip ? -getWidth() : getWidth(), getHeight());
        }
        else { //if has an animation
            stateTime += Main.getFrameRate();
            modelFrame = currentAnimation.getKeyFrame(stateTime, true);
            applyHitbox(currentAnimation.getKeyHitBox(stateTime), flip);
            applyFocalPoint(currentAnimation.getKeyFocalPoint(stateTime), flip);
            if(currentAnimation != idleAnimation && currentAnimation.isAnimationFinished(stateTime)) currentAnimation = idleAnimation;

            batch.draw(modelFrame, flip ? getX() + getWidth() : getX(), getY(), flip ? -getWidth() : getWidth(), getHeight());
        }

        if(Main.inDebugMode) {
            batch.end();
            renderHurtBox();
            batch.begin();
        }
    }
}
