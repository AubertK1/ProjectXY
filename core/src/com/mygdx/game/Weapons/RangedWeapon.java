package com.mygdx.game.Weapons;

/**
 * Only ranged weapons will extend from this class. It helps separate the weapons so that
 * code doesn't unnecessarily overlap in each weapon's class
 */
public class RangedWeapon extends Weapon{
    public RangedWeapon(float x, float y, float width, float height, boolean isCollidable, boolean isVisible) {
        super(x, y, width, height, isCollidable, isVisible);
    }
}
