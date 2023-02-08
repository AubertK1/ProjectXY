package com.mygdx.game.Weapons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;
import com.mygdx.game.OI.Player;
import com.mygdx.game.Object;

/**
 * All weapons will indirectly extend this class by extending either MeleeWeapon or RangedWeapon class.
 * It declares the basic properties every weapon will have (projectile speed, damage, attack speed, etc.)
 * which may be changed in the weapon's separate class
 */
public class Weapon extends Object{
    // the weapon will be assigned an owner when it's picked up
    private Player owner;
    // visual model of fighter
    protected Texture model;
    protected boolean canFall = true;
    protected int verticalVelocity = 0;

    public Weapon(float x, float y, float width, float height, boolean isCollidable, boolean isVisible) {
        super(x, y, width, height, isCollidable, isVisible);
    }

    public void update() {
        float deltaTime = Main.getFrameRate();

        //region gravity
        if (canFall) {
            verticalVelocity += GameScreen.GRAVITY;
            if (verticalVelocity < -1000) verticalVelocity = -1000; //maximum downward velocity
            bounds.y += deltaTime * verticalVelocity;
        }
        //endregion

        if(this.isColliding(Main.gameScreen.stage) == BOTTOMCOLLISION){ //if touching a platform
            canFall = false;
        } else {
            canFall = true;
        }
    }
    /**
     * renders the fighter's model onto the screen
     * @param batch just put batch
     */
    public void render(SpriteBatch batch) {
        update();
        batch.draw(model, getX(), getY(), getWidth(), getHeight());
    }
}
