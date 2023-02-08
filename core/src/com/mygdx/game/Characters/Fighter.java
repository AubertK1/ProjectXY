package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;
import com.mygdx.game.MovingObj;
import com.mygdx.game.OI.Player;
import com.mygdx.game.Weapons.Weapon;

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
    // visual model of fighter
    protected Texture model;

    //fighter's speed
    protected float speed = 400;

    //can the player fall lower
    protected boolean canFall = true;
    //is the player jumping
    protected boolean isJumping = false;
    protected int verticalVelocity = 0;

    protected static int maxJumps = 3;
    protected int jumpsLeft = maxJumps;
    //the next frame they'll be able to jump at
    protected int nextJumpFrame = 0;

    // endregion properties

    public Fighter(float x, float y, float width, float height, boolean isCollidable, boolean isVisible, Player player) {
        super(x, y, width, height, isCollidable, isVisible);
        this.player = player;
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
        setPosition(getX() - Main.getFrameRate() * getSpeed(), getY());
    }
    public void moveRight(){
        setPosition(getX() + Main.getFrameRate() * getSpeed(), getY());
    }
    public void moveDown(){
        setPosition(getX(), getY() - Main.getFrameRate() * (getSpeed() / 2f));
    }

    public void jump(){
        if(jumpsLeft > 0) {
            stopJump();
            isJumping = true;
            canFall = true;
            verticalVelocity = 1660;
            nextJumpFrame = GameScreen.getFrame() + 10;
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

    }

    public void update() {
        float deltaTime = Main.getFrameRate();

        //region gravity
        if (canFall) {
            verticalVelocity += GameScreen.GRAVITY;
            if (verticalVelocity < -1000) verticalVelocity = -1000; //maximum downward velocity
            bounds.y += deltaTime * verticalVelocity;
        }
        //endregion

        //region collision
        if(this.isColliding(Main.gameScreen.stage) == BOTTOMCOLLISION){ //if touching a platform
            canFall = false;
            stopJump();
            resetJumps();
        } else {
            canFall = true;

            if(isJumping && this.isColliding(Main.gameScreen.stage) == TOPCOLLISION){
                stopJump();
                verticalVelocity = 0;
            }
        }

        if(this.isColliding(Main.gameScreen.stage) == LEFTCOLLISION || this.isColliding(Main.gameScreen.stage) == RIGHTCOLLISION){
            stopJump();
            resetJumps();
            verticalVelocity = -135;
        }
        //endregion

        //applying a jump cool down
        if (GameScreen.getFrame() >= nextJumpFrame) {
            isJumping = false;
        }
    }
        /**
         * renders the fighter's model onto the screen
         * @param batch just put batch
         */
    public void render(SpriteBatch batch) {
        player.update();
        batch.draw(model, getX(), getY(), getWidth(), getHeight());
    }

    public Vector2 getPosition(){
        return new Vector2(bounds.x, bounds.y);
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public boolean canFall() {
        return canFall;
    }

    /**
     * assigns this fighter to a player so that it can get its bounds updated
     * 
     * @param player the player this fighter belongs to
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
