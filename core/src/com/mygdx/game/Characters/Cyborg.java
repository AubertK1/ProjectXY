package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.DualAnimation;
import com.mygdx.game.GameScreen;
import com.mygdx.game.HitData;
import com.mygdx.game.KeyBinds;
import com.mygdx.game.OI.Player;
import com.mygdx.game.Projectiles.Projectile;

import java.awt.*;

public class Cyborg extends Fighter{

    DualAnimation sHeavyChargeAnimation;
    boolean doneCharging = false;
    boolean bulletSent = false;

    public Cyborg(float x, float y, Player player) {
        //runs the Fighter class's constructor, so it can set up anything in that constructor
        super(x, y, 1, 1, true, true, player);
        //setting the visual model of the robot
        model = new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_48x.png");
        setSize(model.getWidth(), model.getHeight());
        setHurtbox(13, 0, 21, 38);

        //region setting animations
        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Idle_Sheet.png"), 2, 2, .15f));

        runAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Running_Sheet.png"), 2, 3, .075f);
        jumpAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Jumping_Sheet.png"), 1, 1, .5f);
        fallAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Falling_Sheet.png"), 2, 2, .15f);

        //region attack animations
        //region side light
        sLightAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Attack1_Sheet.png"), 2, 3, .085f);
        sLightAnimation.setHitboxes(new Rectangle(0, 0, 0, 0),
                new Rectangle(0, 19, 5, 8),
                new Rectangle(4, 22, 27, 6),
                new Rectangle(12, 20, 25, 7),
                new Rectangle(23, 20, 15, 7),
                new Rectangle(24, 20, 14, 8));
        sLightAnimation.setFocalPoints(new Point(-1, -1),
                new Point(2, 24),
                new Point(24, 24),
                new Point(33, 24),
                new Point(38, 25),
                new Point(-1, -1));
        //endregion
        //region neutral light
        nLightAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Attack2_Sheet.png"), 1, 7, .055f);
        nLightAnimation.setHitboxes(new Rectangle(0, 0, 0, 0),
                new Rectangle(0, 0, 0, 0),
                new Rectangle(23, 20, 9, 10),
                new Rectangle(23, 20, 13, 10),
                new Rectangle(27, 18, 17, 12),
                new Rectangle(27, 20, 22, 8),
                new Rectangle(27, 20, 21, 8));
        nLightAnimation.setFocalPoints(new Point(-1, -1),
                new Point(-1, -1),
                new Point(26, 28),
                new Point(33, 25),
                new Point(38, 23),
                new Point(43, 20),
                new Point(-1, -1));
        //endregion
        //region down light
        dLightAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Attack3_Sheet.png"), 2, 2, .055f);
        dLightAnimation.setHitboxes(new Rectangle(0, 0, 0, 0),
                new Rectangle(0, 0, 0, 0),
                new Rectangle(24, 7, 12, 8),
                new Rectangle(25, 6, 14, 8));
        dLightAnimation.setFocalPoints(new Point(-1, -1),
                new Point(-1, -1),
                new Point(-1, -1),
                new Point(-1, -1));
        //endregion

        //region side heavy
        sHeavyAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Charge_Release_Sheet.png"), 2, 2, .1f);
        sHeavyAnimation.setHitboxes(new Rectangle(37, 24, 4, 4),
                new Rectangle(37, 24, 4, 4),
                new Rectangle(37, 24, 4, 4),
                new Rectangle(37, 24, 4, 4));

        sHeavyChargeAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Charging_Sheet.png"), 2, 2, .15f);
        //endregion
        //endregion
        //endregion
    }

    // region light attacks
    public void upLightAtk() {

    }

    public void neutralLightAtk() {
        if(currentATK == Attack.NLIGHT && nLightAnimation.isAnimationFinished(stateTime)){
            endAttack();
            return;
        }
        currentATK = Attack.NLIGHT;
        swapAnimation(nLightAnimation);
        Player struckPlayer = player.checkHit();
        boolean hit = struckPlayer != null;
        int atkFrame = nLightAnimation.getKeyFrameIndex(stateTime);
        int direction = isFacingRight ? UPRIGHT : UPLEFT;
        if(hit){
            int damage = attackAlreadyHit ? 0 : 20;
            float deltaT = nLightAnimation.getFrameDuration();
            switch (atkFrame){
                case 1:
                    player.strike(struckPlayer, new HitData().set(damage, 2, 0, direction, 3));
                    break;
                case 2:
                case 3:
                case 4:
                    player.pull(struckPlayer, hitboxFocalPoint, deltaT);
                    player.strike(struckPlayer, new HitData().set(damage, 2, 0, direction, 3));
                    break;
                case 5:
                    player.strike(struckPlayer, new HitData().set(damage, 2, 1.03f, direction, 10));
                    break;
            }
            attackAlreadyHit = true;
        }
    }

    public void sideLightAtk() {
        if(currentATK == Attack.SLIGHT && sLightAnimation.isAnimationFinished(stateTime)){
            endAttack();
            return;
        }
        currentATK = Attack.SLIGHT;
        swapAnimation(sLightAnimation);
        Player struckPlayer = player.checkHit();
        boolean hit = struckPlayer != null;
        int atkFrame = sLightAnimation.getKeyFrameIndex(stateTime);
        int direction = isFacingRight ? RIGHT : LEFT;
        if(isFacingRight) moveRight(); else moveLeft();
        horVelocity *= .7f;
        if(hit){
            int damage = attackAlreadyHit ? 0 : 20;
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
        if(currentATK == Attack.DLIGHT && dLightAnimation.isAnimationFinished(stateTime)){
            endAttack();
            return;
        }
        currentATK = Attack.DLIGHT;
        swapAnimation(dLightAnimation);
        Player struckPlayer = player.checkHit();
        boolean hit = struckPlayer != null;
        int direction = isFacingRight ? DOWNRIGHT : DOWNLEFT;

        if(hit){
            int damage = attackAlreadyHit ? 0 : 20;
            player.strike(struckPlayer, new HitData().set(damage, 2, .5f, direction, 12));
            attackAlreadyHit = true;
        }
    }
    // endregion

    // region heavy attacks
    public void upHeavyAtk() {

    }

    public void neutralHeavyAtk() {

    }

    public void sideHeavyAtk() {
        if(KeyBinds.isKeyPressed(KeyBinds.Keys.HEAVYATTACK, player.getPlayerNum() - 1)) {
            if(!doneCharging) {
                charge();
                return;
            }
        } else doneCharging = true;
        if(currentATK == Attack.SHEAVY && sHeavyAnimation.isAnimationFinished(stateTime)){
            endAttack();
            doneCharging = false;
            bulletSent = false;
            return;
        }
        currentATK = Attack.SHEAVY;
        swapAnimation(sHeavyAnimation);

        if(!bulletSent){
            Projectile bullet = GameScreen.projectilePool.grab();
            boolean flip = !isFacingRight;
            applyHitbox(currentAnimation.getKeyHitBox(stateTime), flip);
            bullet.use(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Charge_Bullet.png"),
                    getHitboxBounds().x, getHitboxBounds().y, 10, 10, flip ? -600 : 600, 0);
            bulletSent = true;
        }
    }
    private void charge(){
        currentATK = Attack.SHEAVY;
        swapAnimation(sHeavyChargeAnimation);
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

}
