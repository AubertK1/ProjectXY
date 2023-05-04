package com.mygdx.game.Characters;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.HitData;
import com.mygdx.game.OI.Player;

/**
 * Every game has a base character that they use to test...this is gonna be our "testing character" per say.
 * Other characters will be balanced around this character.
 */
public class Robot extends Fighter{

    public Robot(float x, float y, Player player) {
        //runs the Fighter class's constructor, so it can set up anything in that constructor
        super(x, y, 1, 1, true, true, player);
        //setting the visual model of the robot
        model = new Texture("assets\\textures\\Security_Robot\\Security_Robot_48x.png");
        setHurtbox(9, 0, 34, 48);
        setSize(model.getWidth(), model.getHeight());

        //region setting animations
        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\textures\\Security_Robot\\Security_Robot_Idle_Sheet.png"), 2, 2, 36));

        runAnimation = animate(new Texture("assets\\textures\\Security_Robot\\Security_Robot_Running_Sheet.png"), 2, 2, 18);


        nLightAnimation = animate(new Texture("assets\\textures\\Violet_Cyborg\\Violet_Cyborg_Attack2_Sheet.png"), 1, 7, 21);
        nLightAnimation.setHitboxes(
                new Rectangle(0, 0, 0, 0),
                new Rectangle(0, 0, 0, 0),
                new Rectangle(23, 20, 9, 10),
                new Rectangle(23, 20, 13, 10),
                new Rectangle(27, 18, 17, 12),
                new Rectangle(27, 20, 22, 8),
                new Rectangle(27, 20, 21, 8));

        //region attack animations
        //endregion
        //endregion
    }


    public void neutralLightAtk() {

        if(currentATK == Attack.NLIGHT && nLightAnimation.isAnimationFinished(stateTime)){
            endAttack(0);
            return;
        }

        currentATK = Attack.NLIGHT;
        swapAnimation(nLightAnimation);
        getStunned(nLightAnimation.getTotalFrames());

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


}
