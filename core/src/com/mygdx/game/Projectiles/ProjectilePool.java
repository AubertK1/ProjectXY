package com.mygdx.game.Projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ProjectilePool {
    private Projectile[] busy, idle;
    private int nextBusyIndex = 0;
    private int nextIdleIndex = 20;

    public ProjectilePool(){
        busy = new Projectile[20];
        idle = new Projectile[20];

        for (int i = 0; i < idle.length; i++) {
            idle[i] = new Projectile();
        }
    }

    public void update(){
        int expirationTime = 300;
        for (int i = 0; i < nextBusyIndex; i++) {
            busy[i].activeTime++;
            if(busy[i].activeTime > expirationTime) free(busy[i]);
        }

    }
    public Projectile grab(){
        for (int i = idle.length - 1; i >= 0; i--) {
            if(idle[i] != null){
                Projectile p = idle[i];
                busy[nextBusyIndex] = p;
                idle[i] = null;

                nextBusyIndex++;
                nextIdleIndex--;

                return p;
            }
        }
        return null;
    }
    public void free(Projectile p){
        int removedIndex = 19;
        for (int i = 0; i < busy.length; i++) { //looking for the projectile
            if(busy[i] == p){
                Projectile p2 = busy[i];
                p2.wipe();
                idle[nextIdleIndex] = p2;

                busy[i] = null;

                nextBusyIndex--;
                nextIdleIndex++;
                removedIndex = i;

                break;
            }
        }
        Projectile[] b2 = new Projectile[busy.length];
        for (int i = 0; i < nextBusyIndex + 1; i++) { //getting rid of any gaps in the non-null indexes in busy[]
            if(i < removedIndex){
                b2[i] = busy[i];
            }
            else if(i > removedIndex){
                b2[i - 1] = busy[i];
            }
        }
        busy = b2;
    }

    public void renderProjectiles(SpriteBatch batch){
        for (int i = 0; i < nextBusyIndex; i++) {
            busy[i].render(batch);
        }
    }
}
