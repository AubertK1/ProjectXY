package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.HitData;
import com.mygdx.game.OI.Player;

import java.awt.*;

public class Cyborg extends Fighter{

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
        //endregion
    }

    // region light attacks
    public void upLightAtk() {

    }

    public void neutralLightAtk() {
//        currentATK = Attack.NLIGHT;

    }

    public void sideLightAtk() {
        if(sLightAnimation.isAnimationFinished(stateTime)){
            currentATK = Attack.NOATTACK;
            stateTime = 0;
            return;
        }
        swapAnimation(sLightAnimation);
        Player struckPlayer = player.checkHit();
        boolean hit = struckPlayer != null;
        currentATK = Attack.SLIGHT;
        int atkFrame = sLightAnimation.getKeyFrameIndex(stateTime);
        int direction = isFacingRight ? RIGHTCOLLISION : LEFTCOLLISION;
        if(isFacingRight) moveRight();
        else moveLeft();
        horVelocity *= .7f;
        if(hit){
            int damage = attackAlreadyHit ? 0 : 5;
            float deltaT = sLightAnimation.getFrameDuration();
            switch (atkFrame){
                case 1:
                    player.strike(struckPlayer, new HitData().set(damage, 2, .00f, direction, 0));
                    break;
                case 2:
                case 3:
                case 4:
                    player.strike(struckPlayer, new HitData().set(damage, 2, .00f, direction, 0));
                    player.pull(struckPlayer, hitboxFP, deltaT);
                    break;
                case 5:
                    player.strike(struckPlayer, new HitData().set(damage, 2, 1.1f, direction, 0));
                    break;
            }
            attackAlreadyHit = true;
        }
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

}
