package com.mygdx.game.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Hurtbox;
import com.mygdx.game.OI.Player;

/**
 * All fighters will extend from this class. It declares the basic properties every fighter will have
 * (speed, health, damage, etc.) which may be changed in the fighter's separate class
 */
public class Fighter {
    //the fighter will be assigned to a player when chosen
    Player player;
    //visual model of fighter
    protected Texture model;
    Hurtbox hurtbox;
    //fighter's position stored as an x and y coordinate
    public Vector2 position = new Vector2();
    //fighter's speed
    public float speed = 300;
    //can the player fall through fall lower
    public boolean canFall = true;
    //is the player jumping
    public boolean isJumping = false;
    public static int maxJumpFrames = 30;
    private int remainingJumpFrames = maxJumpFrames;
    
    public Fighter(Player player) {
        this.player = player;
    }


    /**
     * These will be extended and based on the fighter
     */
    //region light attacks
    public void upLightAtk() {
    }
    public void neutralLightAtk() {
    }
    public void sideLightAtk() {
    }
    public void downLightAtk() {
    }
    //endregion

    //region heavy attacks
    public void upHeavyAtk(){
    }
    public void neutralHeavyAtk(){
    }
    public void sideHeavyAtk(){
    }
    public void downHeavyAtk(){
    }
    //endregion

    //region air attacks
    public void upAirAtk(){
    }
    public void neutralAirAtk(){
    }
    public void sideAirAtk(){
    }
    public void downAirAtk(){
    }
    public void recoveryAtk(){
    }
    //endregion

    public void block(){

    }

    public void jump(float delaTime) {
        canFall = false;
        isJumping = true;
        //during the starting frames...
        if(remainingJumpFrames > 15) {
            int x = remainingJumpFrames - 15;
            //this is a workaround for now. Later I'm gonna have a gravity variable and apply that here instead
            float jumpParabolaEq = ((-.06666f*(x*x)) + (2*x)); //this is so their jump slows at the end
            position.y += delaTime * jumpParabolaEq * 125; //to edit jump height change the number at the end
            remainingJumpFrames--;
        }
        //after halfway, you start falling again
        else if (remainingJumpFrames > 0) {
            canFall = true;
            remainingJumpFrames--;
        }
        //once it's done, everything is reset
        else {
            canFall = true;
            isJumping = false;
            remainingJumpFrames = maxJumpFrames;
        }
    }

    /**
     * renders the fighter's model onto the screen
     * @param batch just put batch
     */
    public void render(SpriteBatch batch) {
        player.update(Gdx.graphics.getDeltaTime());
        batch.draw(model, position.x, position.y);
    }

    /**
     * same function, but you can scale the fighter to make it larger
     * @param scale how much you want to times it by
     */
    public void render(SpriteBatch batch, float scale) {
        player.update(Gdx.graphics.getDeltaTime());
        float newWidth = model.getWidth()*scale;
        float newHeight = model.getHeight()*scale;
        batch.draw(model, position.x, position.y, newWidth, newHeight);
    }

    public void setPosition(float x, float y) {
        position.x = x;
        position.y = y;
    }

    /**
     * assigns this fighter to a player so that it can get its position updated
     * @param player the player this fighter belongs to
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
