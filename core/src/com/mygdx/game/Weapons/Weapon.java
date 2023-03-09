package com.mygdx.game.Weapons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameScreen;
import com.mygdx.game.HitData;
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
            if(!owner.getFighter().isFacingRight()) setPosition(owner.getFighter().getHBX() - getHBWidth(), owner.getFighter().getHBY() + (owner.getFighter().getHBHeight() * 0.36f));
            else setPosition(owner.getFighter().getHBX() + owner.getFighter().getHBWidth(), owner.getFighter().getHBY() + (owner.getFighter().getHBHeight() * 0.36f));
            return;
        }

        applyPhysics();

        //region collisions
        if(this.isCollidingWith(Main.gameScreen.platform) == BOTTOMCOLLISION){ //if touching a platform
            canFall = false;
        } else {
            canFall = true;
        }
        if(this.isCollidingWith(Main.gameScreen.platform) == LEFTCOLLISION || this.isCollidingWith(Main.gameScreen.platform) == RIGHTCOLLISION){
            stop();
        }
        //endregion
    }

    public HitData hit(){
        return new HitData().set(10, 2, 1.4f, NOCOLLISION);
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

    public void spawn(){
        horVelocity = 0;
        vertVelocity = 0;
        setPosition(GameScreen.spawnCenter.x, GameScreen.spawnCenter.y);
    }

    /**
     * renders the fighter's model onto the screen
     * @param batch just put batch
     */
    public void render(SpriteBatch batch) {
        update();

        if(currentAnimation == null) { //if no animation...
            if (owner == null) batch.draw(model, getX(), getY(), getWidth(), getHeight());
            else { //this draws the weapon flipped depending on which way the fighter is facing
                boolean flip = owner.getFighter().isFacingRight();
                batch.draw(model, flip ? getX() + getWidth() : getX(), getY(), flip ? -getWidth() : getWidth(), getHeight());
            }
        }
        else { //if has an animation
            stateTime += Main.getFrameRate();
            modelFrame = currentAnimation.getKeyFrame(stateTime, true);
            if(currentAnimation != idleAnimation && currentAnimation.isAnimationFinished(stateTime)) currentAnimation = idleAnimation;

            if (owner == null) batch.draw(modelFrame, getX(), getY(), getWidth(), getHeight());
            else { //this draws the weapon flipped depending on which way the fighter is facing
                boolean flip = owner.getFighter().isFacingRight();
                batch.draw(modelFrame, flip ? getX() + getWidth() : getX(), getY(), flip ? -getWidth() : getWidth(), getHeight());
            }
        }

        if(Main.inDebugMode) {
            batch.end();
            renderHurtBox();
            batch.begin();
        }
    }
}
