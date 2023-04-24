package com.mygdx.game.Projectiles;

import com.mygdx.game.Object;

public class PlasmaBallProjectile extends Projectile{

    public PlasmaBallProjectile() {
        super();
    }

    @Override
    protected void proc(Object collidedObj) {
        System.out.println("hit Player");
    }
}
