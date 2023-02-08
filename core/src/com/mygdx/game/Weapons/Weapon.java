package com.mygdx.game.Weapons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;
import com.mygdx.game.MovingObj;
import com.mygdx.game.OI.Player;

/**
 * All weapons will indirectly extend this class by extending either MeleeWeapon or RangedWeapon class.
 * It declares the basic properties every weapon will have (projectile speed, damage, attack speed, etc.)
 * which may be changed in the weapon's separate class
 */
public class Weapon extends MovingObj {
    // the weapon will be assigned an owner when it's picked up
    private Player owner;

    public Weapon(float x, float y, float width, float height, boolean isCollidable, boolean isVisible) {
        super(x, y, width, height, isCollidable, isVisible);
    }

    public void update() {
        if(owner != null){
            setPosition(owner.getFighter().getX() - getWidth(), owner.getFighter().getY() + 20);
            return;
        }

        float deltaTime = Main.getFrameRate();

        //region default movement
        if (canFall) {
            vertVelocity += GameScreen.GRAVITY;
            if (vertVelocity < -1000) vertVelocity = -1000; //maximum downward velocity
            bounds.y += deltaTime * vertVelocity;
        }
        bounds.x += deltaTime * horVelocity;
        slowDown();
        //endregion

        //region collisions
        if(this.isColliding(Main.gameScreen.stage) == BOTTOMCOLLISION){ //if touching a platform
            canFall = false;
        } else {
            canFall = true;
        }
        if(this.isColliding(Main.gameScreen.stage) == LEFTCOLLISION || this.isColliding(Main.gameScreen.stage) == RIGHTCOLLISION){
            stop();
        }
        //endregion
    }

    public void launch(float hVelo, float vVelo){
        vertVelocity = vVelo;
        horVelocity = hVelo;
    }

    public void setOwner(Player owner){
        this.owner = owner;
    }
    public Player getOwner(){
        return owner;
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
