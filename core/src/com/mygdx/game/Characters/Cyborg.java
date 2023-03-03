package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.OI.Player;

public class Cyborg extends Fighter{

    public Cyborg(float x, float y, Player player) {
        //runs the Fighter class's constructor, so it can set up anything in that constructor
        super(x, y, 1, 1, true, true, player);
        //setting the visual model of the robot
        model = new Texture("assets\\textures\\Violet_Cyborg.png");
        setSize(model.getWidth(), model.getHeight());

        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\textures\\Violet_Cyborg_Idle_Sheet.png"), 2, 2, .15f));

        runAnimation = animate(runSheet = new Texture("assets\\textures\\Violet_Cyborg_Running_Sheet.png"), 2, 3, .075f);
    }
}
