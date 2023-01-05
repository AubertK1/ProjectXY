package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * This will be used to create stages and platforms
 */
public class Platform {
    //this is the visual part of the platform
    Texture model;
    //this is the physical part of the platform
    Hurtbox hurtbox;

    public Platform(Texture texture) {
        model = texture;
        hurtbox = new Hurtbox();
    }
}
