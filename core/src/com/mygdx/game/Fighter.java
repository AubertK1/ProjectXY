package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * All fighters will extend from this class. It declares the basic properties every fighter will have
 * (speed, health, damage, etc.) which may be changed in the fighter's separate class
 */
public class Fighter {
    //the fighter will be assigned to a player when chosen
    Player player;
    //visual model of fighter
    Texture model;
    Hurtbox hurtbox;
    //fighter's position stored as an x and y coordinate
    Vector2 position = new Vector2();
    //fighter's speed
    float speed = 300;
    public Fighter(Player player) {
        this.player = player;
    }


    /**
     * These will be extended and based on the fighter
     */
    //region light attacks
    public void upLightAtk(){
    }
    public void neutralLightAtk(){
    }
    public void sideLightAtk(){
    }
    public void downLightAtk(){
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

    /**
     * renders the fighter's model onto the screen
     * @param batch just put batch
     */
    public void render(SpriteBatch batch){
        player.update(Gdx.graphics.getDeltaTime());
        batch.draw(model, position.x, position.y);
    }
    /**
     * same function, but you can scale the fighter to make it larger
     * @param scale how much you want to times it by
     */
    public void render(SpriteBatch batch, float scale){
        player.update(Gdx.graphics.getDeltaTime());
        float newWidth = model.getWidth()*scale;
        float newHeight = model.getHeight()*scale;
        batch.draw(model, position.x, position.y, newWidth, newHeight);
    }
    public void setPosition(float x, float y){
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
