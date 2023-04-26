package com.mygdx.game.Projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.*;
import com.mygdx.game.OI.Player;
import com.mygdx.game.Object;

public class Projectile extends MovingObj {
    protected int activeTime = 0;
    protected HitData hitData = new HitData();
    protected Player owner;

    public Projectile() {
        super(-1, -1, 0, 0, true, false);
    }

    public void use(Player owner, Texture model, float centerX, float centerY, float width, float height, float hVelo, float vVelo){
        this.model = model;
        setOwner(owner);

        setPosition(centerX - (width/2f), centerY - (height/2f));
        setHBPosition(0, 0);
        setSize(width, height);
        setHBSize(width, height);

        launch(hVelo, vVelo);
    }

    public void setHitData(HitData hitData){
        this.hitData = hitData;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void launch(float hVelo, float vVelo){
        vertVelocity = vVelo;
        horVelocity = hVelo;
    }

    public void wipe(){
        activeTime = 0;

        setPosition(-1, -1);
        setSize(-1, -1);
        launch(0, 0);
    }

    protected Object checkCollision(){
        for (Player player: GameScreen.getPlayers()) { //fighter collisions
            if(player == owner) continue;
            if (!this.isCollidingWith(player.getFighter())[NOCOLLISION]) { //if hits a fighter
                return player.getFighter();
            }
        }
        for (Platform platform: GameScreen.getPlatforms()) { //platform collisions
            if (!this.isCollidingWith(platform)[NOCOLLISION]) { //if hits a platform
                return platform;
            }
        }

        return null;
    }

    /**
     * The effect from hitting something. Will vary from different projectiles
     * @param collidedObj the object that's being hit by the projectile
     */
    protected void proc(Object collidedObj){

    }

    public void free(){
        GameScreen.projectilePool.free(this);
    }
    private void update(){
        setPosition(getX() + (Main.getFrameRate() * horVelocity), getY());

        Object obj = checkCollision();
        if(obj != null) proc(obj);
    }
    public void render(SpriteBatch batch) {
        update();

        if(model == null) return;
        batch.draw(model, getX(), getY(), getWidth(), getHeight());
    }
}
