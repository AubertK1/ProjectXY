package com.mygdx.game.Weapons;

/**
 * By extending RangedWeapon it also indirectly extends Weapon and inherits both of the classes' properties
 */
public class Shotgun extends RangedWeapon{
    public Shotgun(float x, float y, float width, float height, boolean isCollidable, boolean isVisible) {
        super(x, y, width, height, isCollidable, isVisible);
    }
}
