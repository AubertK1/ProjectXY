package com.mygdx.game.Weapons;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
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

    private int damage = 5;

    public Weapon(float x, float y, float width, float height, boolean isCollidable, boolean isVisible) {
        super(x, y, width, height, isCollidable, isVisible);
    }

    public void update() {
        if(owner != null){
            if(!owner.getFighter().isFacingRight()) setPosition(owner.getFighter().getX() - getWidth(), owner.getFighter().getY() + 20);
            else if(owner.getFighter().isFacingRight()){
//                Sprite flippedModel = new Sprite(model);
//                flippedModel.flip(true, false);
//                Drawable m2 = (Drawable) flippedModel;
//                model = flippedModel;
                setPosition(owner.getFighter().getX() + owner.getFighter().getWidth(), owner.getFighter().getY() + 20);
            }
            return;
        }

        applyPhysics();

        //region collisions
        if(this.isColliding(Main.gameScreen.platform) == BOTTOMCOLLISION){ //if touching a platform
            canFall = false;
        } else {
            canFall = true;
        }
        if(this.isColliding(Main.gameScreen.platform) == LEFTCOLLISION || this.isColliding(Main.gameScreen.platform) == RIGHTCOLLISION){
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

    public int getDamage(){
        return damage;
    }

    /**
     * renders the fighter's model onto the screen
     * @param batch just put batch
     */
    public void render(SpriteBatch batch) {
        update();
        if(owner == null) batch.draw(model, getX(), getY(), getWidth(), getHeight());
        else { //this draws the weapon flipped depending on which way the fighter is facing
            boolean flip = owner.getFighter().isFacingRight();
            batch.draw(model, flip ? getX() + getWidth() : getX(), getY(), flip ? -getWidth() : getWidth(), getHeight());
        }
//        batch.draw(model, getX(), getY(), getWidth(), getHeight(), (int) getX(), (int) getY(), (int) getWidth(), (int) getHeight(), owner.getFighter().isFacingRight(), false);
    }
}
