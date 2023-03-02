package com.mygdx.game.Weapons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.OI.Player;

/**
 * By extending MeleeWeapon it also indirectly extends Weapon and inherits both of the classes' properties
 */
public class Sword extends MeleeWeapon{

    public Sword(float x, float y) {
        super(x, y, 1, 1, true, true);
        //setting the visual model of the robot
        model = new Texture("assets\\stock-textures\\stocksword.png");
        setSize(model.getWidth(), model.getHeight());
    }
}
