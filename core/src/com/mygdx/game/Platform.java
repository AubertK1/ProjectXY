package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * This will be used to create stages and platforms
 */
public class Platform extends Object {
    // this is the visual part of the platform
    Texture model;

    /**
     * 
     * @param x
     * @param y
     * @param texture
     */
    public Platform(int x, int y, Texture texture) {
        super(x, y, texture.getWidth(), texture.getHeight(), true, true);
        model = texture;
    }

    /**
     * makes a centered platform
     * 
     * @param texture
     */
    public Platform(Texture texture) {
        super((Gdx.graphics.getWidth() / 2f) - (texture.getWidth() / 2f),
                (Gdx.graphics.getHeight() * .4f) - (texture.getHeight()),
                texture.getWidth(), texture.getHeight(),
                true, true);
        model = texture;
    }

    public void render(SpriteBatch batch){
        batch.draw(model, getX(), getY(), getWidth(), getHeight());

        if(Main.inDebugMode) {
            batch.end();
            renderHitBox();
            batch.begin();
        }
    }
}
