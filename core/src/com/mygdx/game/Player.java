package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The actual player. This is what is directly being affected by the user, and it uses inputs
 * to call the Fighter class methods.
 * The user controls the Player. the Fighter is simply the physical manifestation of the Player.
 */
public class Player {
    Fighter fighter;

    public Player() {
    }
    public void setFighter(Fighter fighter){
        this.fighter = fighter;
    }


    /**
     * renders the fighter's model
     * @param batch just put batch
     * @param x the x coordinate of where to draw it
     * @param y the y coordinate of where to draw it
     */
    public void renderFighter(SpriteBatch batch, float x, float y){
        fighter.render(batch, x, y);
    }
    /**
     * same function, but you can scale the fighter to make it larger
     * @param scale how much you want to times it by
     */
    public void renderFighter(SpriteBatch batch, float scale){
        fighter.render(batch, scale);
    }
}
