package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Player;

public class Vampire extends Fighter{

    public Vampire(Player player) {
        //runs the Fighter class's constructor, so it can set up anything in that constructor
        super(player);
        //setting the visual model of the vampire
        model = new Texture("assets\\textures\\stockvampire.png");
    }
}
