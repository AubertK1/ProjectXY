package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MovingObj;
import com.mygdx.game.DualAnimation;
import com.mygdx.game.Platform;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;
import com.mygdx.game.OI.Player;

import java.awt.Point;

/**
 * All fighters will extend from this class. It declares the basic properties
 * every fighter will have
 * (speed, health, damage, etc.) which may be changed in the fighter's separate
 * class
 */
public class Fighter extends MovingObj{

    // region properties

    // the fighter will be assigned to a player when chosen
    protected Player player;

    //region stats
    protected String name;
    protected static int maxJumps = 2;
    protected float speed = 500;
    protected int damage = 10;
    protected int maxHealth = 100;
    protected int health = maxHealth;
    protected int fortitude = 15;
    //endregion

    //region states of being
    protected boolean isAlive = true; //is the player alive
    protected boolean isJumping = false; //is the player jumping
    protected boolean isBlocking = false; //is the player blocking
    protected boolean isFacingRight = true;

    protected boolean isStunned = false;
    //endregion

    protected int jumpsLeft = maxJumps;
    //the next frame they'll be able to jump at
    protected int nextJumpFrame = 0;
    protected int nextUnstunFrame = 0;

    // endregion properties

    //region animations
    DualAnimation runAnimation;
    DualAnimation jumpAnimation;
    DualAnimation fallAnimation;
    //attacks
    DualAnimation nLightAnimation;
    DualAnimation sLightAnimation;
    DualAnimation dLightAnimation;
    DualAnimation nHeavyAnimation;
    DualAnimation sHeavyAnimation;
    DualAnimation dHeavyAnimation;
    //endregion

    //region attacks
    public enum Attack {
        NLIGHT, SLIGHT, DLIGHT,
        NHEAVY, SHEAVY, DHEAVY,

        NOATTACK
    }
    protected Attack currentATK = Attack.NOATTACK;
    protected boolean attackAlreadyHit = false;
    //region projectiles
    boolean projectileCharged = false;
    boolean projectileSent = false;
    //endregion

    protected int nextATKFrame = 0;
    //endregion

    public Fighter(float x, float y, float width, float height, boolean isCollidable, boolean isVisible, Player player) {
        super(x, y, width, height, isCollidable, isVisible);
        this.player = player;
    }

    public void update() {
        // float deltaTime = Main.getFrameRate();

        //region collisions
        int i = 0;
        boolean canFallChanged = false;
        for (Platform platform: GameScreen.getPlatforms()) { //platform collisions
            if (this.isCollidingWith(platform)[BOTTOMCOLLISION]) { //if landing on a platform
                canFall = false;
                canFallChanged = true;
                stopJump();
                resetJumps();

                this.pushOutOf(platform, UP);
            } else if (!canFallChanged && i == GameScreen.getPlatforms().size()-1){
                canFall = true;
            }

            if (this.isCollidingWith(platform)[TOPCOLLISION]) { //if hitting a platform from the bottom
                stopJump();
                vertVelocity = 0;

                // this.pushOutOf(platform, DOWN);
            }

            if (this.isCollidingWith(platform)[LEFTCOLLISION]) { //if hitting a platform from the side
                if(horVelocity < 0) horVelocity = 0;
                stopJump();
                resetJumps();
                vertVelocity = -135;

                this.pushOutOf(platform, RIGHT);
            }
            if (this.isCollidingWith(platform)[RIGHTCOLLISION]) { //if hitting a platform from the side
                if(horVelocity > 0) horVelocity = 0;
                stopJump();
                resetJumps();
                vertVelocity = -135;

                this.pushOutOf(platform, LEFT);
            }

            i++;
        }   
        //endregion

        //region gravity
        applyPhysics();
        if(vertVelocity < -100 && !isAttacking()) swapAnimation(fallAnimation);

        //endregion

        //applying a jump cool down
        if (GameScreen.getFrame() >= nextJumpFrame) isJumping = false;
        if (GameScreen.getFrame() >= nextUnstunFrame) isStunned = false;
    }

    /**
     * These will be extended and based on the fighter
     */
    // region attacks

    public void initiateAtk(int atkIndexConstant, int recoveryFrames){
        Attack[] atks = Attack.values();

        Attack ATK = atks[atkIndexConstant];
        DualAnimation atkAnim = null;
        switch (atkIndexConstant){
            case 0:
                atkAnim = nLightAnimation;
                break;
            case 1:
                atkAnim = sLightAnimation;
                break;
            case 2:
                atkAnim = dLightAnimation;
                break;
            case 3:
                atkAnim = nHeavyAnimation;
                break;
            case 4:
                atkAnim = sHeavyAnimation;
                break;
            case 5:
                atkAnim = dHeavyAnimation;
                break;
        }
        if(atkAnim == null) return;

        if(currentATK == ATK && atkAnim.isAnimationFinished(stateTime)){
            endAttack(recoveryFrames);
            return;
        }
        currentATK = ATK;
        swapAnimation(atkAnim);
        beStunned(atkAnim.getRemainingFrames(stateTime));
    }

    public void initiateProjectileAtk(int atkIndexConstant, int recoveryFrames){
        Attack[] atks = Attack.values();

        Attack ATK = atks[atkIndexConstant];
        DualAnimation atkAnim = null;
        switch (atkIndexConstant){
            case 0:
                atkAnim = nLightAnimation;
                break;
            case 1:
                atkAnim = sLightAnimation;
                break;
            case 2:
                atkAnim = dLightAnimation;
                break;
            case 3:
                atkAnim = nHeavyAnimation;
                break;
            case 4:
                atkAnim = sHeavyAnimation;
                break;
            case 5:
                atkAnim = dHeavyAnimation;
                break;
        }
        if(atkAnim == null) return;

        if(currentATK == ATK && atkAnim.isAnimationFinished(stateTime) && projectileSent){ //this line is the only change
            endAttack(recoveryFrames);
            return;
        }
        currentATK = ATK;
        swapAnimation(atkAnim);
        beStunned(atkAnim.getRemainingFrames(stateTime));
    }

    // region light attacks
    public void upLightAtk() {

    }

    public void neutralLightAtk() {

    }

    public void sideLightAtk() {

    }

    public void downLightAtk() {
         
    }
    // endregion

    // region heavy attacks
    public void upHeavyAtk() {
         
    }

    public void neutralHeavyAtk() {
         
    }

    public void sideHeavyAtk() {
         
    }

    public void downHeavyAtk() {
         
    }
    // endregion

    // region air attacks
    public void upAirAtk() {
    }

    public void neutralAirAtk() {
    }

    public void sideAirAtk() {
    }

    public void downAirAtk() {
    }

    public void recoveryAtk() {
    }
    // endregion

    // endregion

    //region movement
    public void moveLeft(){
        horVelocity = -speed;
        isFacingRight = false;

        if(runAnimation != null && currentATK == Attack.NOATTACK && vertVelocity == 0) swapAnimation(runAnimation);
    }
    public void moveRight(){
        horVelocity = speed;
        isFacingRight = true;

        if(runAnimation != null && currentATK == Attack.NOATTACK && vertVelocity == 0) swapAnimation(runAnimation);
    }
    public void moveDown(){
        setPosition(getX(), getY() - Main.getFrameRate() * (speed / 2f));
    }

    public void jump(){
        if(jumpsLeft > 0) {
            stopJump();
            isJumping = true;
            canFall = true;
            vertVelocity = 1660;
            nextJumpFrame = GameScreen.getFrame() + 6;
            jumpsLeft--;

            if(jumpAnimation != null) swapAnimation(jumpAnimation, true);
        }
    }
    public void stopJump(){
        isJumping = false;
    }
    public void resetJumps(){
        jumpsLeft = maxJumps;
    }

    public void pullTo(Point point, float time){
        if(point.x == -1) return;
        float deltaX = point.x - (hurtboxBounds.x + (hurtboxBounds.width / 2f));
        float deltaY = point.y - (hurtboxBounds.y + (hurtboxBounds.height / 2f));

        horVelocity = deltaX / time;
        vertVelocity = deltaY / time;
    }
    //endregion

    public void block() {
        isBlocking = true;
    }

    public void endAttack(int recoveryFrames){
        //ending the attack and resetting values
        attackAlreadyHit = false;
        currentATK = Attack.NOATTACK;
        stateTime = currentAnimation.getTotalFrames() / 60f; //ending animation

        recover(recoveryFrames);
    }

    private void recover(int recoveryFrames){
        nextATKFrame = GameScreen.getFrame() + recoveryFrames;
        if(isStunned)
            beStunned(recoveryFrames); //if stunned, extend it through the recovery frames
    }

    public void beDamaged(int damage){
        health -= damage;

        if (health <= 0) {
            die();
        }
    }
    public void beKnockedBack(int direction, float multiplier, boolean preferRight){
        float baseHorKB = 800;
        float baseVertKB = -GameScreen.GRAVITY + baseHorKB;

        if(multiplier < 0){ //negative multipliers do not scale with health.
            multiplier = -multiplier;
        } else {
            multiplier = multiplier * ((float)(maxHealth - health) * (1 / 50f));
        }

        canFall = true;
        switch (direction){
            case LEFT:
                horVelocity = -baseHorKB * multiplier * 1f;
                vertVelocity = baseVertKB * multiplier * .65f;
                break;
            case RIGHT:
                horVelocity = baseHorKB * multiplier * 1f;
                vertVelocity = baseVertKB * multiplier * .65F;
                break;
            case UPLEFT:
                horVelocity = -baseHorKB * multiplier * .74f;
                vertVelocity = baseVertKB * multiplier * .9f;
                break;
            case UPRIGHT:
                horVelocity = baseHorKB * multiplier * .74f;
                vertVelocity = baseVertKB * multiplier * .9f;
                break;
            case DOWNLEFT:
                horVelocity = -baseHorKB * multiplier * .74f;
                vertVelocity = -baseVertKB * multiplier * .9f;
                break;
            case DOWNRIGHT:
                horVelocity = baseHorKB * multiplier * .74f;
                vertVelocity = -baseVertKB * multiplier * .9f;
                break;
            case UP:
                horVelocity = (preferRight ? baseHorKB : -baseHorKB) * multiplier * .35f;
                vertVelocity = baseVertKB * multiplier;
                break;
            case DOWN:

                horVelocity = baseHorKB * multiplier * .35f;
                vertVelocity = -baseVertKB * multiplier;
                break;
        }
    }

    /**
     * Stops fighter from receiving player input
     * @param duration the amount of frames the fighter will be stunned
     */
    public void beStunned(int duration){
        if(duration == 0) return;
//        if(!isInHitStun) stop();

        isStunned = true;
        nextUnstunFrame = GameScreen.getFrame() + duration;
    }

    public void die(){
        horVelocity *= 2;
        vertVelocity *= 2;

        float v = 4000;
        if(horVelocity > 0 && horVelocity < v) horVelocity = v;
        else if(horVelocity < 0 && horVelocity > -v) horVelocity = -v;
        if(vertVelocity >= 0 && vertVelocity < v) vertVelocity = v;
        else if(vertVelocity < 0 && vertVelocity > -v) vertVelocity = -v;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public boolean canFall() {
        return canFall;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public int getHealth(){
        return health;
    }

    public int getFortitude() {
        return fortitude;
    }

    public int getDamage() {
        return damage;
    }
    public boolean isStunned() {
        return isStunned;
    }


    public boolean isAttacking(){
        return !(currentATK == Attack.NOATTACK);
    }

    public void reset(){
        health = maxHealth;

        horVelocity = 0;
        vertVelocity = 0;

        resetJumps();

        setPosition(GameScreen.spawnCenter.x, GameScreen.spawnCenter.y);
    }

    public Attack getCurrentATK(){
        return currentATK;
    }

    /**
     * assigns this fighter to a player so that it can get its bounds updated
     * 
     * @param player the player this fighter belongs to
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName(){
        return name;
    }

    public int getNextATKFrame(){
        return nextATKFrame;
    }
    /**
     * renders the fighter's model onto the screen
     * @param batch just put batch
     */
    public void render(SpriteBatch batch) {
        update();

        //this draws the fighter flipped depending on which way it is facing
        boolean flip = !isFacingRight();
        if(currentAnimation == null) { //if no animation...
            //this draws the fighter flipped depending on which way it is facing
            batch.draw(model, flip ? getX() + getWidth() : getX(), getY(), flip ? -getWidth() : getWidth(), getHeight());
        }
        else { //if has an animation
            stateTime += Main.getFrameRate();
            modelFrame = currentAnimation.getKeyFrame(stateTime, true);
            applyHitbox(currentAnimation.getKeyHitBox(stateTime), flip);
            applyFocalPoint(currentAnimation.getKeyFocalPoint(stateTime), flip);
            if(currentAnimation != idleAnimation && currentAnimation.isAnimationFinished(stateTime)) currentAnimation = idleAnimation;

            batch.draw(modelFrame, flip ? getX() + modelFrame.getRegionWidth() * getScale() : getX(), getY(),
                    flip ? -modelFrame.getRegionWidth() * getScale() : modelFrame.getRegionWidth() * getScale(), modelFrame.getRegionHeight() * getScale());
        }

        if(Main.inDebugMode) {
            batch.end();
            renderOutlines();
            batch.begin();
        }
    }
}
