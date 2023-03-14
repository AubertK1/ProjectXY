package com.mygdx.game.Weapons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.*;
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

        //region collisions
        int i = 0;
        boolean canFallChanged = false;
        for (Platform platform: GameScreen.getPlatforms()) { //platform collisions
            if (this.isCollidingWith(platform) == BOTTOMCOLLISION) { //if landing on a platform
                canFall = false;
                canFallChanged = true;
            } else if (!canFallChanged && i == GameScreen.getPlatforms().size()-1){
                canFall = true;
            }

            if (this.isCollidingWith(platform) == TOPCOLLISION) { //if hitting a platform from the bottom
                vertVelocity = 0;
            }

            if (this.isCollidingWith(platform) == LEFTCOLLISION) { //if hitting a platform from the side
                if(horVelocity < 0) horVelocity = 0;
            }
            if (this.isCollidingWith(platform) == RIGHTCOLLISION) { //if hitting a platform from the side
                if(horVelocity > 0) horVelocity = 0;
            }

            i++;
        }
        //endregion

        applyPhysics();
    }

    public HitData hit(){
        return new HitData().set(10, 2, 1.4f, NOCOLLISION, 0);
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
