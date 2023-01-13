package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.OI.Player;

/**
 * Every game has a base character that they use to test...this is gonna be our "testing character" per say.
 * Other characters will be balanced around this character.
 */
public class Robot extends Fighter{

    public Robot(Player player) {
        //runs the Fighter class's constructor, so it can set up anything in that constructor
        super(player);
        //setting the visual model of the robot
        model = new Texture("assets\\textures\\stockrobot.png");
    }
}
