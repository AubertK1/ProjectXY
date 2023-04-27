package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.OI.Player;

import java.awt.*;

/**
 * Every game has a base character that they use to test...this is gonna be our "testing character" per say.
 * Other characters will be balanced around this character.
 */
public class Robot extends Fighter{

    public Robot(float x, float y, Player player) {
        //runs the Fighter class's constructor, so it can set up anything in that constructor
        super(x, y, 1, 1, true, true, player);
        //setting the visual model of the robot
        model = new Texture("assets\\textures\\Security_Robot\\Security_Robot_48x.png");
        setHurtbox(9, 0, 34, 48);
        setSize(model.getWidth(), model.getHeight());

        //region setting animations
        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\textures\\Security_Robot\\Security_Robot_Idle_Sheet.png"), 2, 2, .15f));

        runAnimation = animate(new Texture("assets\\textures\\Security_Robot\\Security_Robot_Running_Sheet.png"), 2, 2, .075f);

        //region attack animations
        //endregion
        //endregion
    }
}
