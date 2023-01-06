package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The actual player. This is what is directly being affected by the user, and it uses inputs
 * to call the Fighter class methods.
 * The user controls the Player. the Fighter is simply the physical manifestation of the Player.
 */
public class Player {
    Fighter fighter;
    //if this is player 1, player 2, etc.
    int playerNum;

    public Player(int playerNum) {
        this.playerNum = playerNum;
    }

    /**
     * sets this player's fighter
     * @param fighter
     */
    public void setFighter(Fighter fighter){
        this.fighter = fighter;
        fighter.setPlayer(this);
    }

    /**
     * updates the visuals or anything that needs to be updated every frame
     * @param delaTime the frame rate I think (just put Gdx.graphics.getDeltaTime())
     */
    public void update(float delaTime) {
        //registers player's input
        if(playerNum == 1) { //if player 1...
            if (Gdx.input.isKeyPressed(Input.Keys.D)) fighter.position.x += delaTime * fighter.speed;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) fighter.position.x -= delaTime * fighter.speed;
            if (Gdx.input.isKeyPressed(Input.Keys.W)) fighter.position.y += delaTime * fighter.speed;
            if (Gdx.input.isKeyPressed(Input.Keys.S)) fighter.position.y -= delaTime * fighter.speed;
        }
        
        else if(playerNum == 2) { //if player 2...
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) fighter.position.x += delaTime * fighter.speed;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) fighter.position.x -= delaTime * fighter.speed;
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) fighter.position.y += delaTime * fighter.speed;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) fighter.position.y -= delaTime * fighter.speed;
        }

    }

    /**
     * renders the fighter's model
     * @param batch just put batch
     */
    public void renderFighter(SpriteBatch batch){
        fighter.render(batch);
    }
    /**
     * same function, but you can scale the fighter to make it larger
     * @param scale how much you want to times it by
     */
    public void renderFighter(SpriteBatch batch, float scale){
        fighter.render(batch, scale);
    }
}
