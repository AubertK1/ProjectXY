package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 * Every game has a base character that they use to test...this is gonna be our "testing character" per say.
 * Other characters will be balanced around this character.
 */
public class Robot extends Fighter{

    public Robot() {
        //setting the visual model of the robot
        model = new Texture("assets\\textures\\stockrobot.png");
    }
}
