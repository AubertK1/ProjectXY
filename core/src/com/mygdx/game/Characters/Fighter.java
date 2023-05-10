package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.*;
import com.mygdx.game.OI.Player;

import java.awt.Point;

import static com.mygdx.game.GameScreen.getFrame;

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
    public int maxHealth = 100;
    protected int health = maxHealth;
    protected int fortitude = 15;
    //endregion

    //region states of being
    protected boolean isAlive = true; //is the player alive
    protected boolean isJumping = false; //is the player jumping
    protected boolean isBlocking = false; //is the player blocking
    protected boolean isFacingRight = true;
    protected boolean isStunned = false;
    private boolean isOnWall = false;
    private boolean isFrozen = false;
    //endregion

    protected int jumpsLeft = maxJumps;
    //the next frame they'll be able to jump at
    protected int nextJumpFrame = 0;
    protected int nextUnstunFrame = 0;
    protected int nextUnfreezeFrame = 0;

    private int lives =3;

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
    //region attack holding
    private int nextAttackHoldLimitFrame = 0;
    private boolean attackCharged = false;
    protected boolean attackSent = false;
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
                isOnWall = true;

                this.pushOutOf(platform, RIGHT);
            }
            else if (this.isCollidingWith(platform)[RIGHTCOLLISION]) { //if hitting a platform from the side
                if(horVelocity > 0) horVelocity = 0;
                stopJump();
                resetJumps();
                vertVelocity = -135;
                isOnWall = true;

                this.pushOutOf(platform, LEFT);
            } else isOnWall = false;

            i++;
        }   
        //endregion

        //region gravity
        if(!isFrozen) applyPhysics();
        if(vertVelocity < -100 && !isAttacking()) swapAnimation(fallAnimation);

        //endregion

        //applying a jump cool down
        if (getFrame() >= nextJumpFrame) isJumping = false;
        if (getFrame() >= nextUnstunFrame) isStunned = false;
        if (getFrame() >= nextUnfreezeFrame) isFrozen = false;
    }

    /**
     * These will be extended and based on the fighter
     */
    // region attacks

    /**
     * @return returns false any time the function has to end early
     */
    public boolean initiateAtk(int atkIndexConstant, int recoveryFrames) {
        return initiateAtk(atkIndexConstant, recoveryFrames, null);
    }

    /**
     * @return returns false any time the function has to end early
     */
    public boolean initiateAtk(int atkIndexConstant, int recoveryFrames, DualAnimation holdingAnimation){
        //region retrieving attack type
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
        if(atkAnim == null) return false;
        //endregion

        //region holding/delaying attack
        int typeOfAttack = KeyBinds.Keys.LIGHTATTACK;
        if(ATK == Attack.NHEAVY || ATK == Attack.SHEAVY ||ATK == Attack.DHEAVY)
            typeOfAttack = KeyBinds.Keys.HEAVYATTACK;

        if(nextAttackHoldLimitFrame < getFrame() && !attackCharged)
            nextAttackHoldLimitFrame = getFrame() + 90;
        if(getFrame() >= nextAttackHoldLimitFrame) attackCharged = true;
        if(KeyBinds.isKeyPressed(typeOfAttack, player.getPlayerNum() - 1)) { //if they're holding down the attack key
            if(!attackCharged) { //so they can't charge again while it's being sent out
                if(holdingAnimation != null) holdAtk(ATK, holdingAnimation, false);
                else holdAtk(ATK, atkAnim, true);
                return false;
            }
        } else{
            attackCharged = true;
            nextAttackHoldLimitFrame = getFrame() - 1;
        }
        //endregion

        if(currentATK == ATK && atkAnim.isAnimationFinished(stateTime) && attackSent){
            endAttack(recoveryFrames);
            return false;
        }
        currentATK = ATK;
        swapAnimation(atkAnim);
        beStunned(atkAnim.getRemainingFrames(stateTime));

        return true;
    }


    protected void holdAtk(Attack ATK, DualAnimation atkAnimation, boolean shouldFreezeAnimation){
        currentATK = ATK;
        swapAnimation(atkAnimation, shouldFreezeAnimation); //this either should hold the animation at frame 0 or let it play through;
        beStunned(atkAnimation.getRemainingFrames(stateTime));
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
            nextJumpFrame = getFrame() + 6;
            jumpsLeft--;

            if(isOnWall){
                if(isFacingRight)
                    setPosition(getX() - 15, getY()); //If on left side of wall
                else
                    setPosition(getX() + 15, getY());; //If on right side of wall
                vertVelocity = 2000;
            }

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
        attackSent = false;
        attackCharged = false;
        currentATK = Attack.NOATTACK;
        stateTime = currentAnimation.getTotalFrames() / 60f; //ending animation

        recover(recoveryFrames);
    }

    private void recover(int recoveryFrames){
        nextATKFrame = getFrame() + recoveryFrames;
        if(isStunned)
            beStunned(recoveryFrames); //if stunned, extend it through the recovery frames
    }

    public void beDamaged(int damage){
        int oldHealth = health;
        health -= damage;

        if (health <= 0) {
            if(oldHealth>0){
                lives--;
            }
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
        nextUnstunFrame = getFrame() + duration;
    }
    public void beFrozen(int duration){
        isFrozen = true;
        beStunned(duration);
        nextUnfreezeFrame = getFrame() + duration;
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

            float frameWidth = modelFrame.getRegionWidth() * getScale(), frameHeight = modelFrame.getRegionHeight() * getScale();
            batch.draw(modelFrame, flip ? getX() + getWidth() : getX(), getY(),
                    flip ? -frameWidth : frameWidth, frameHeight);
        }

        if(Main.inDebugMode) {
            batch.end();
            renderOutlines();
            batch.begin();
        }
    }

    public int getLives(){
        return lives;
    }
}
