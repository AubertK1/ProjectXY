package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameScreen;
import com.mygdx.game.HitData;
import com.mygdx.game.Main;
import com.mygdx.game.MovingObj;
import com.mygdx.game.OI.Player;

/**
 * All fighters will extend from this class. It declares the basic properties
 * every fighter will have
 * (speed, health, damage, etc.) which may be changed in the fighter's separate
 * class
 */
public class Fighter extends MovingObj{

    // region properties

    // the fighter will be assigned to a player when chosen
    private Player player;

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
    //endregion

    protected int jumpsLeft = maxJumps;
    //the next frame they'll be able to jump at
    protected int nextJumpFrame = 0;

    // endregion properties

    public Fighter(float x, float y, float width, float height, boolean isCollidable, boolean isVisible, Player player) {
        super(x, y, width, height, isCollidable, isVisible);
        this.player = player;
    }

    public void update() {
        float deltaTime = Main.getFrameRate();

        //region gravity
        if (canFall) {
            vertVelocity += GameScreen.GRAVITY;
            if (vertVelocity < -1000) vertVelocity = -1000; //maximum downward velocity
            bounds.y += deltaTime * vertVelocity;
        }
        bounds.x += deltaTime * horVelocity;
        slowDown();
        //endregion

        //region collision
        if(this.isColliding(Main.gameScreen.platform) == BOTTOMCOLLISION){ //if touching a platform
            canFall = false;
            stopJump();
            resetJumps();
        } else {
            canFall = true;

            if(isJumping && this.isColliding(Main.gameScreen.platform) == TOPCOLLISION){
                stopJump();
                vertVelocity = 0;
            }
        }

        if(this.isColliding(Main.gameScreen.platform) == LEFTCOLLISION || this.isColliding(Main.gameScreen.platform) == RIGHTCOLLISION){
            stopJump();
            resetJumps();
            vertVelocity = -135;
        }
        //endregion

        //applying a jump cool down
        if (GameScreen.getFrame() >= nextJumpFrame) {
            isJumping = false;
        }
    }

    /**
     * These will be extended and based on the fighter
     */
    // region attacks

    // region light attacks
    public HitData upLightAtk() {
        return new HitData();
    }

    public HitData neutralLightAtk() {
        return new HitData().set(5, 2, 1.1f, TOPCOLLISION);
    }

    public HitData sideLightAtk() {
        int direction = RIGHTCOLLISION;
        if (horVelocity < 0) direction = LEFTCOLLISION;
        else if (horVelocity == 0) direction = isFacingRight() ? RIGHTCOLLISION : LEFTCOLLISION;
        return new HitData().set(5, 2, 1.1f, direction);
    }

    public HitData downLightAtk() {
        return new HitData();
    }
    // endregion

    // region heavy attacks
    public HitData upHeavyAtk() {
        return new HitData();
    }

    public HitData neutralHeavyAtk() {
        return new HitData();
    }

    public HitData sideHeavyAtk() {
        return new HitData();
    }

    public HitData downHeavyAtk() {
        return new HitData();
    }
    // endregion

    // region air attacks
    public HitData upAirAtk() {
        return new HitData();
    }

    public HitData neutralAirAtk() {
        return new HitData();
    }

    public HitData sideAirAtk() {
        return new HitData();
    }

    public HitData downAirAtk() {
        return new HitData();
    }

    public HitData recoveryAtk() {
        return new HitData();
    }
    // endregion

    // endregion

    //region movement
    public void moveLeft(){
        horVelocity = -speed;
        isFacingRight = false;
    }
    public void moveRight(){
        horVelocity = speed;
        isFacingRight = true;
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
        }
    }
    public void stopJump(){
        isJumping = false;
    }
    public void resetJumps(){
        jumpsLeft = maxJumps;
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

    public void reset(){
        health = maxHealth;

        horVelocity = 0;
        vertVelocity = 0;

        setPosition(GameScreen.spawnCenter.x, GameScreen.spawnCenter.y);
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
        batch.draw(model, flip ? getX() + getWidth() : getX(), getY(), flip ? -getWidth() : getWidth(), getHeight());
    }
}
