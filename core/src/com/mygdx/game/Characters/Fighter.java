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
    protected static int maxJumps = 2;
    protected float speed = 500;
    protected int damage = 10;
    protected int maxHealth = 100;
    protected int health = maxHealth;
    protected int fortitude = 15;
    //endregion

    //region states of being
    protected boolean isAlive = true; //is the player alive
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
    DualAnimation dLightAnimation;
    DualAnimation nHeavyAnimation;
    DualAnimation sHeavyAnimation;
    DualAnimation dHeavyAnimation;
    //endregionNH

    //region attacks
    public enum Attack {
        NOATTACK,
        NLIGHT, SLIGHT, DLIGHT,
        NHEAVY, SHEAVY, DHEAVY,
    }
    protected Attack currentATK = Attack.NOATTACK;
    protected boolean attackAlreadyHit = false;
    //endregion

    public Fighter(float x, float y, float width, float height, boolean isCollidable, boolean isVisible, Player player) {
        super(x, y, width, height, isCollidable, isVisible);
        this.player = player;
    }

    public void update() {
        // float deltaTime = Main.getFrameRate();

        //region collisions
        int i = 0;
        boolean canFallChanged = false;
        for (Platform platform: GameScreen.getPlatforms()) { //platform collisions
            if (this.isCollidingWith(platform) == BOTTOMCOLLISION) { //if landing on a platform
                canFall = false;
                canFallChanged = true;
                stopJump();
                resetJumps();
            } else if (!canFallChanged && i == GameScreen.getPlatforms().size()-1){
                canFall = true;
            }

            if (this.isCollidingWith(platform) == TOPCOLLISION) { //if hitting a platform from the bottom
                stopJump();
                vertVelocity = 0;
            }

            if (this.isCollidingWith(platform) == LEFTCOLLISION) { //if hitting a platform from the side
                if(horVelocity < 0) horVelocity = 0;
                stopJump();
                resetJumps();
                vertVelocity = -135;
            }
            if (this.isCollidingWith(platform) == RIGHTCOLLISION) { //if hitting a platform from the side
                if(horVelocity > 0) horVelocity = 0;
                stopJump();
                resetJumps();
                vertVelocity = -135;
            }

            i++;
        }
        //endregion

        //region gravity
        applyPhysics();
        if(vertVelocity < -100 && !isAttacking()) swapAnimation(fallAnimation);

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

    public void endAttack(){
        //ending the attack and resetting values
        attackAlreadyHit = false;
        currentATK = Attack.NOATTACK;
        isInHitStun = false; //fixme may cause unintended glitches
        stateTime = 0;
    }

    public void takeDamage(int damage){
        health -= damage;

        if (health <= 0) {
            reset();
        }
    }
    public void knockBack(int direction, float multiplier, boolean preferRight){
        float baseHorKB = 800;
        float baseVertKB = -GameScreen.GRAVITY + baseHorKB;

        canFall = true;
        switch (direction){
            case LEFT:
                horVelocity = -baseHorKB * multiplier * 1f;
                vertVelocity = baseVertKB * multiplier * .65f;
                break;
            case RIGHT:
                horVelocity = baseHorKB * multiplier * 1f;
                vertVelocity = baseVertKB * multiplier * .65F;
                break;
            case UPLEFT:
                horVelocity = -baseHorKB * multiplier * .74f;
                vertVelocity = baseVertKB * multiplier * .9f;
                break;
            case UPRIGHT:
                horVelocity = baseHorKB * multiplier * .74f;
                vertVelocity = baseVertKB * multiplier * .9f;
                break;
            case DOWNLEFT:
                horVelocity = -baseHorKB * multiplier * .74f;
                vertVelocity = -baseVertKB * multiplier * .9f;
                break;
            case DOWNRIGHT:
                horVelocity = baseHorKB * multiplier * .74f;
                vertVelocity = -baseVertKB * multiplier * .9f;
                break;
            case UP:
                horVelocity = (preferRight ? baseHorKB : -baseHorKB) * multiplier * .35f;
                vertVelocity = baseVertKB * multiplier;
                break;
            case DOWN:

                horVelocity = baseHorKB * multiplier * .35f;
                vertVelocity = -baseVertKB * multiplier;
                break;
        }
    }
    public void getStunned(int duration){
        if(duration == 0) return;
        if(!isInHitStun) stop();

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
    public boolean isStunned() {
        return isInHitStun;
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

    public Player getPlayer() {
        return player;
    }

    /**
     * renders the fighter's model onto the screen
     * @param batch just put batch
     */
    public void render(SpriteBatch batch) {
        update();

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
            renderOutlines();
            batch.begin();
        }
    }
}
