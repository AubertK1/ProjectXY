package com.mygdx.game.Projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

public class ProjectilePool {
    private LinkedList<Projectile> busy, idle;

    public ProjectilePool(){
        busy = new LinkedList<>();
        idle = new LinkedList<>();
    }

    public void update(){
        int expirationTime = 300;
        for (int i = 0; i < busy.size(); i++) {
            busy.get(i).activeTime++;
            if(busy.get(i).activeTime > expirationTime) free(busy.get(i));
        }
    }
    public Projectile grab(Class projectileType){
        for (int i = idle.size() - 1; i >= 0; i--) {
            if (idle.get(i).getClass() == projectileType) {
                Projectile p = idle.get(i);
                busy.add(p);
                idle.remove(p);

                return p;
            }
        }
        try {
            //if there aren't any already existing projectiles of the Type
            Object p = projectileType.newInstance();
            busy.add((Projectile) p);

            return (Projectile) p;
        } catch (InstantiationException | IllegalAccessException ie) {
            ie.printStackTrace();
        }

        return null;
    }
    public void free(Projectile p){
        busy.remove(p);
        p.wipe();
        idle.add(p);
    }

    public void renderProjectiles(SpriteBatch batch){
        for (Projectile p : busy) {
            p.render(batch);
        }
    }
}
