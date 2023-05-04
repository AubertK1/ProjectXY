package com.mygdx.game.Projectiles;

import com.mygdx.game.Characters.Fighter;
import com.mygdx.game.Object;
import com.mygdx.game.Platform;

public class PlasmaBallProjectile extends Projectile{

    public PlasmaBallProjectile() {
        super();
    }

    @Override
    protected void proc(Object collidedObj) {
        if(collidedObj instanceof Platform){
            free();
        }
        else if(collidedObj instanceof Fighter){
            hitData.direction = this.horVelocity > 0 ? RIGHT : LEFT;
            owner.strike(((Fighter) collidedObj).getPlayer(), hitData);
            free();
        }
    }
}
