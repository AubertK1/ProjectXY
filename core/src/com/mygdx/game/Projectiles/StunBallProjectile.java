package com.mygdx.game.Projectiles;

import com.mygdx.game.Characters.Fighter;
import com.mygdx.game.Object;
import com.mygdx.game.Platform;

public class StunBallProjectile  extends Projectile{

    public StunBallProjectile() {
        super();
    }

    protected void update(){
        super.update();

        if(activeTime % 30 == 0){
            setSize(getWidth() + 6, getHeight() + 6);
            setPosition(getX() - 3, getY() - 3);

            hitData.hitStunDuration += 10;
            hitData.knockbackMultiplier += .15f;
        }
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
