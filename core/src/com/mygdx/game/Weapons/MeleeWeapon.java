package com.mygdx.game.Weapons;

/**
 * Only melee weapons will extend from this class. It helps separate the weapons so that
 * code doesn't unnecessarily overlap in each weapon's class
 */
public class MeleeWeapon extends Weapon{
    public MeleeWeapon(float x, float y, float width, float height, boolean isCollidable, boolean isVisible) {
        super(x, y, width, height, isCollidable, isVisible);
    }
}
