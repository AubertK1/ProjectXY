package com.mygdx.game.Characters;

import com.badlogic.gdx.math.Rectangle;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Constants.FighterConstants;
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

        //region stats
        name = "Sarge";
        maxHealth = 125;
        health = maxHealth;
        //endregion

        //region setting animations
        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\textures\\Security_Robot\\Security_Robot_Idle_Sheet.png"), 2, 2, 36));

        runAnimation = animate(new Texture("assets\\textures\\Security_Robot\\Security_Robot_Running_Sheet.png"), 2, 2, 18);

        //region attack animations

        //region neutral light
        nLightAnimation = animate(new Texture("assets\\textures\\Security_Robot\\Security_Robot_Attack1_Sheet.png"), 3, 2, 24);
        nLightAnimation.setHitboxes(
                null,
                null,
                null,
                new Rectangle(14, 10, 36, 36),
                new Rectangle(14, 10, 36, 36),
                new Rectangle(14, 10, 36, 36));

        sLightAnimation = nLightAnimation;

        //endregion
        //region neutral light
        nHeavyAnimation = animate(new Texture("assets\\textures\\Security_Robot\\Security_Robot_Whip_Attack_Sheet.png"), 3, 2, 30);
        nHeavyAnimation.setHitboxes(
                null,
                null,
                null,
                null,
                new Rectangle(40, 28, 88, 20),
                new Rectangle(40, 28, 80, 18));
        //endregion
        //endregion
        //endregion
    }


    public void neutralHeavyAtk() {
        initiateAtk(FighterConstants.kNHEAVYIndex, 0);
        attackSent = true;

        Player struckPlayer = player.checkHit();
        boolean hit = struckPlayer != null;
        int direction = !isFacingRight ? UPRIGHT : UPLEFT;

        if(hit){
            int damage = attackAlreadyHit ? 0 : 15;
            player.strike(struckPlayer, new HitData().set(damage, 2, -.75f, direction, 20));
            attackAlreadyHit = true;
        }
    }

    public void sideLightAtk() {
        initiateAtk(FighterConstants.kSLIGHTIndex, 12);
        attackSent = true;

        Player struckPlayer = player.checkHit();
        boolean hit = struckPlayer != null;
        int direction = isFacingRight ? DOWNRIGHT : DOWNLEFT;

        if(isFacingRight) moveRight(); else moveLeft();
        horVelocity *= .75f;

        if(hit){
            int damage = attackAlreadyHit ? 0 : 20;
            player.strike(struckPlayer, new HitData().set(damage, 2, 2, direction, 20));
            attackAlreadyHit = true;
        }
    }

    public void neutralLightAtk() {
        initiateAtk(FighterConstants.kNLIGHTIndex, 12);
        attackSent = true;

        Player struckPlayer = player.checkHit();
        boolean hit = struckPlayer != null;
        int direction = isFacingRight ? DOWNRIGHT : DOWNLEFT;

        if(hit){
            int damage = attackAlreadyHit ? 0 : 20;
            player.strike(struckPlayer, new HitData().set(damage, 2, 2, direction, 20));
            attackAlreadyHit = true;
        }
    }

}
