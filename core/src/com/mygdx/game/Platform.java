package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * This will be used to create stages and platforms
 */
public class Platform {
    //this is the visual part of the platform
    Texture texture;
    //this is the physical part of the platform
    PlatformModel model;

    public Platform(Texture texture) {
        this.texture = texture;
        model = new PlatformModel();
    }
}
