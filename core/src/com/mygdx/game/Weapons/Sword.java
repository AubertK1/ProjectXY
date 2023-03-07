package com.mygdx.game.Weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.GameScreen;
import com.mygdx.game.HitData;
import com.mygdx.game.Main;

/**
 * By extending MeleeWeapon it also indirectly extends Weapon and inherits both of the classes' properties
 */
public class Sword extends MeleeWeapon{
    Texture hitSheet = new Texture("assets\\stock-textures\\swordhitsheet2.png");
    protected Animation<TextureRegion> hitAnimation;

    public Sword(float x, float y) {
        super(x, y, 1, 1, true, true);
        //setting the visual model of the robot
        model = new Texture("assets\\stock-textures\\stocksword.png");
        setHitbox(0, 0, 32, 32);
        setSize(model.getWidth(), model.getHeight());

        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\stock-textures\\SwordSheet2.png"), 2, 2, .15f));
        hitAnimation = animate(hitSheet, 2, 2, .075f);
    }

    @Override
    public HitData hit() {
        swapAnimation(hitAnimation);
        return super.hit();
    }
}
