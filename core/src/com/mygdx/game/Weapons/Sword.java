package com.mygdx.game.Weapons;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.DualAnimation;
import com.mygdx.game.HitData;

/**
 * By extending MeleeWeapon it also indirectly extends Weapon and inherits both of the classes' properties
 */
public class Sword extends MeleeWeapon{
    Texture hitSheet = new Texture("assets\\stock-textures\\swordhitsheet2.png");
    protected DualAnimation hitAnimation;

    public Sword(float x, float y) {
        super(x, y, 1, 1, true, true);
        //setting the visual model of the robot
        model = new Texture("assets\\stock-textures\\stocksword.png");
        setHurtbox(0, 0, 32, 32);
        setSize(model.getWidth(), model.getHeight());

        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\stock-textures\\SwordSheet2.png"), 2, 2, 36));
        hitAnimation = animate(hitSheet, 2, 2, 18);
    }

    @Override
    public HitData hit() {
        swapAnimation(hitAnimation);
        return super.hit();
    }
}
