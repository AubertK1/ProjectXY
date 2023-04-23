package com.mygdx.game.Projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Main;
import com.mygdx.game.MovingObj;

public class Projectile extends MovingObj {
    protected int activeTime = 0;

    public Projectile() {
        super(-1, -1, 0, 0, true, false);
    }

    public void use(Texture model, float centerX, float centerY, float width, float height, float hVelo, float vVelo){
        this.model = model;

        setPosition(centerX - (width/2f), centerY - (height/2f));
        setSize(width, height);
        launch(hVelo, vVelo);
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

    private void update(){
        setPosition(getX() + (Main.getFrameRate() * horVelocity), getY());
    }
    public void render(SpriteBatch batch) {
        update();

        if(model == null) return;
        batch.draw(model, getX(), getY(), getWidth(), getHeight());
    }
}
