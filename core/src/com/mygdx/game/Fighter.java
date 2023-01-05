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
    Texture model;
    Hurtbox hurtbox;
    Vector2 position = new Vector2();
    float speed = 300;
    public Fighter() {

    }

    public void update(float delaTime){
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) position.x += delaTime * speed;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) position.x -= delaTime * speed;
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) position.y += delaTime * speed;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) position.y -= delaTime * speed;
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

    public void render(SpriteBatch batch,float x,float y){
        batch.draw(model, x, y);
    }
    public void render(SpriteBatch batch, float scale){
        update(Gdx.graphics.getDeltaTime());
        float newWidth = model.getWidth()*scale;
        float newHeight = model.getHeight()*scale;
        batch.draw(model, position.x, position.y, newWidth, newHeight);
    }
    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;
    }
}
