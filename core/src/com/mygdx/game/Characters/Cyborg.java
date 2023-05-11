package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.DualAnimation;
import com.mygdx.game.GameScreen;
import com.mygdx.game.HitData;
import com.mygdx.game.KeyBinds;
import com.mygdx.game.Constants.CyborgConstants;
import com.mygdx.game.OI.Player;
import com.mygdx.game.Projectiles.PlasmaBallProjectile;
import com.mygdx.game.Projectiles.StunBallProjectile;
import com.mygdx.game.Constants.FighterConstants;

import java.awt.Point;

public class Cyborg extends Fighter {

    DualAnimation sHeavyChargeAnimation;
    DualAnimation nHeavyChargeAnimation;
    float plasmaBallScale = 1;

    public Cyborg(float x, float y, Player player) {
        //runs the Fighter class's constructor, so it can set up anything in that constructor
        super(x, y, 1, 1, true, true, player);
        //setting the visual model of the robot
        model = new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_48x.png");
        setSize(model.getWidth(), model.getHeight());
        setHurtbox(
            CyborgConstants.kHurtBoxXOffSet,
            CyborgConstants.kHurtBoxYOffSet,
            CyborgConstants.kHurtBoxWidth, 
            CyborgConstants.kHurtBoxHeight);

        //region stats
        name = "Violet";
        maxHealth = 100;
        health = maxHealth;
        //endregion

        //region setting animations
        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Idle_Sheet.png"), 2, 2, 36));

        runAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Running_Sheet.png"), 2, 3, 27);
        jumpAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Jumping_Sheet.png"), 1, 1, 30);
        fallAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Falling_Sheet.png"), 2, 2, 36);

        //region attack animations
        //region side light
        sLightAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Attack1_Sheet.png"), 2, 3, 30);
        sLightAnimation.setHitboxes(
                null,
                new Rectangle(0, 19, 5, 8),
                new Rectangle(4, 22, 27, 6),
                new Rectangle(12, 20, 25, 7),
                new Rectangle(23, 20, 15, 7),
                new Rectangle(24, 20, 14, 8));
        sLightAnimation.setFocalPoints(null,
                new Point(2, 24),
                new Point(24, 24),
                new Point(33, 24),
                new Point(38, 25),
                null);
        //endregion
        //region neutral light
        nLightAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Attack2_Sheet.png"), 1, 7, 21);
        nLightAnimation.setHitboxes(
                null,
                null,
                new Rectangle(23, 20, 9, 10),
                new Rectangle(23, 20, 13, 10),
                new Rectangle(27, 18, 17, 12),
                new Rectangle(27, 20, 22, 8),
                new Rectangle(27, 20, 21, 8));
        nLightAnimation.setFocalPoints(null,
                null,
                new Point(26, 28),
                new Point(33, 25),
                new Point(38, 23),
                new Point(43, 20),
                null);
        //endregion
        //region down light
        dLightAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Attack3_Sheet.png"), 2, 2, 13);
        dLightAnimation.setHitboxes(
                null,
                null,
                new Rectangle(24, 7, 12, 8),
                new Rectangle(25, 6, 14, 8));
        //endregion

        //region neutral heavy
        nHeavyAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Stun_Release_Sheet.png"), 2, 2, 12);
        nHeavyAnimation.setHitboxes(new Rectangle(37, 26, 4, 4),
                new Rectangle(37, 26, 4, 4),
                new Rectangle(37, 26, 4, 4),
                new Rectangle(37, 26, 4, 4));

        nHeavyChargeAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Charging_Stun_Sheet.png"), 2, 2, 36);
        //endregion
        //region side heavy
        sHeavyAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Charge_Release_Sheet.png"), 2, 2, 24);
        sHeavyAnimation.setHitboxes(new Rectangle(37, 26, 4, 4),
                new Rectangle(37, 26, 4, 4),
                new Rectangle(37, 26, 4, 4),
                new Rectangle(37, 26, 4, 4));

        sHeavyChargeAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Charging_Sheet.png"), 2, 2, 36);
        //endregion
        //endregion
        //endregion
    }

    // region light attacks
    public void upLightAtk() {

    }

    public void neutralLightAtk() {
        if(!initiateAtk(FighterConstants.kNLIGHTIndex, 0))
            return;
        attackSent = true;

        Player struckPlayer = player.checkHit();
        boolean hit = struckPlayer != null;
        int atkFrame = nLightAnimation.getKeyFrameIndex(stateTime);
        int direction = isFacingRight ? UPRIGHT : UPLEFT;
        if(hit){
            int damage = attackAlreadyHit ? 0 : 14;
            float deltaT = nLightAnimation.getFrameDuration();
            switch (atkFrame){
                case 1:
                    player.strike(struckPlayer, new HitData().set(damage, 2, 0, direction, 3));
                    break;
                case 2:
                case 3:
                case 4:
                    player.strike(struckPlayer, new HitData().set(damage, 2, 0, direction, 3));
                    player.pull(struckPlayer, hitboxFocalPoint, deltaT);
                    break;
                case 5:
                    player.strike(struckPlayer, new HitData().set(damage, 2, 1.03f, direction, 10));
                    break;
            }
            attackAlreadyHit = true;
        }
    }

    public void sideLightAtk() {
        if(!initiateAtk(FighterConstants.kSLIGHTIndex, 16))
            return;
        attackSent = true;

        Player struckPlayer = player.checkHit();
        boolean hit = struckPlayer != null;
        int atkFrame = sLightAnimation.getKeyFrameIndex(stateTime);
        int direction = isFacingRight ? RIGHT : LEFT;
        
        if(isFacingRight) moveRight(); else moveLeft();
        horVelocity *= .7f;
        if(hit){
            int damage = attackAlreadyHit ? 0 : 12;
            float deltaT = sLightAnimation.getFrameDuration();
            switch (atkFrame){
                case 1:
                    player.strike(struckPlayer, new HitData().set(damage, 2, 0, direction, 18));
                    break;
                case 2:
                case 3:
                case 4:
                    player.strike(struckPlayer, new HitData().set(damage, 2, 0, direction, 18));
                    player.pull(struckPlayer, hitboxFocalPoint, deltaT);
                    break;
                case 5:
                    player.strike(struckPlayer, new HitData().set(damage, 2, 1.5f, direction, 18));
                    break;
            }
            attackAlreadyHit = true;
        }
    }

    public void downLightAtk() {
        if(!initiateAtk(FighterConstants.kDLIGHTIndex, 0))
            return;
        attackSent = true;

        Player struckPlayer = player.checkHit();
        boolean hit = struckPlayer != null;
        int direction = isFacingRight ? DOWNRIGHT : DOWNLEFT;

        if(hit){
            int damage = attackAlreadyHit ? 0 : 5;
            player.strike(struckPlayer, new HitData().set(damage, 2, -.5f, direction, 12));
            attackAlreadyHit = true;
        }
    }
    // endregion

    // region heavy attacks
    public void upHeavyAtk() {

    }

    public void neutralHeavyAtk() {
        if (!initiateAtk(FighterConstants.kNHEAVYIndex, 6, nHeavyChargeAnimation))
            return;

        if(!attackSent){
            StunBallProjectile plasmaBall = (StunBallProjectile) GameScreen.projectilePool.grab(StunBallProjectile.class);
            boolean flip = !isFacingRight;
            applyHitbox(currentAnimation.getKeyHitBox(stateTime), flip);
            plasmaBall.use(this.player, new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Stun_Bullet.png"),
                    getHitboxBounds().x, getHitboxBounds().y, 10, 10, flip ? -600 : 600, 0);
            plasmaBall.setHitData(new HitData().set(6, 1, 0, NODIRECTION, 5));
            attackSent = true;
        }
    }

    public void sideHeavyAtk() {
        if (!initiateAtk(FighterConstants.kSHEAVYIndex, 8, sHeavyChargeAnimation))
            return;

        if(!attackSent){
            PlasmaBallProjectile plasmaBall = (PlasmaBallProjectile) GameScreen.projectilePool.grab(PlasmaBallProjectile.class);
            boolean flip = !isFacingRight;
            applyHitbox(currentAnimation.getKeyHitBox(stateTime), flip);
            plasmaBall.use(this.player, new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Charge_Bullet.png"),
                    getHitboxBounds().x, getHitboxBounds().y, 10 * (plasmaBallScale * plasmaBallScale), 10 * (plasmaBallScale * plasmaBallScale), flip ? -600 : 600, 0);
            plasmaBall.setHitData(new HitData().set((int) (3 * plasmaBallScale), 1, .65f * plasmaBallScale, NODIRECTION, (int) (10 * plasmaBallScale)));
            attackSent = true;
        }
    }

    public void downHeavyAtk() {

    }

    @Override
    public void endAttack(int recoveryFrames){
        super.endAttack(recoveryFrames);
        plasmaBallScale = 1f;
    }

    @Override
    protected void holdAtk(Attack ATK, DualAnimation atkAnimation, boolean shouldFreezeAnimation) {
        super.holdAtk(ATK, atkAnimation, shouldFreezeAnimation);

        //region Side Heavy
        if(plasmaBallScale < 2f && GameScreen.getFrame() % 8 == 0)
            plasmaBallScale += .1f; //grows every 8 frames and maxes at 2x
        //endregion
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

}
