package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.OI.Player;

public class Vampire extends Fighter{

    public Vampire(float x, float y, Player player) {
        //runs the Fighter class's constructor, so it can set up anything in that constructor
        super(x, y, 1, 1, true, true, player);
        //setting the visual model of the vampire
        model = new Texture("assets\\textures\\Vampire_Bot.png");
        setSize(model.getWidth(), model.getHeight());
    }
}
