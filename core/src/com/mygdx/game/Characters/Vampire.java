package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Constants;
import com.mygdx.game.GameScreen;
import com.mygdx.game.HitData;
import com.mygdx.game.KeyBinds;
import com.mygdx.game.OI.Player;
import com.mygdx.game.Projectiles.StunBallProjectile;

import java.util.ArrayList;
import java.util.LinkedList;

public class Vampire extends Fighter{
    private LinkedList<Float> pastXs = new LinkedList<>();
    private LinkedList<Float> pastYs = new LinkedList<>();

    private VampireShadow shadow;

    public Vampire(float x, float y, Player player) {
        //runs the Fighter class's constructor, so it can set up anything in that constructor
        super(x, y, 1, 1, true, true, player);
        //setting the visual model of the vampire
        model = new Texture("assets\\textures\\Vampire_Bot\\Vampire_Bot_48x.png");
        //initializing shadow before setting the Vampire's traits
        shadow = new VampireShadow(this);
        shadow.beInvisible(120);
        setHurtbox(5, 0, 38, 46); // 5, -2, 38, 46
        setSize(model.getWidth(), model.getHeight());

        //region stats
        name = "Atticus";
        maxHealth = 125;
        health = maxHealth;
        //endregion

        //region setting animations
        swapAnimation(idleAnimation = animate(idleSheet = new Texture("assets\\textures\\Vampire_Bot\\Vampire_Bot_Idle_Sheet.png"), 2, 3, 36));

        //region neutral heavy
        nHeavyAnimation = animate(new Texture("assets\\textures\\Vampire_Bot\\Vampire_Bot_Idle_Sheet.png"), 2, 3, 12);
        //endregion
        //endregion
    }

    //region extending functions for shadow
    @Override
    public void update(){
        super.update();

        //region flight
        //descends slower if jump is held in air
        if(vertVelocity < -100 && KeyBinds.isKeyPressed(KeyBinds.Keys.JUMP, player.getPlayerNum() - 1)){
            vertVelocity *= .5f;
        }
        //endregion

        //region updating past positions lists
        pastXs.add(getX());
        pastYs.add(getY());
        if(pastXs.size() == 121){ //can go back a max of 181 frames
            pastXs.remove(); //remove the first index / the oldest x position
            pastYs.remove(); //remove the first index / the oldest y position
        }
        //endregion
        shadow.setPosition(pastXs.element(), pastYs.element());
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        shadow.render(batch);
    }
    @Override
    public void setSize(float width, float height){
        super.setSize(width, height);
        shadow.setSize(width, height);
    }
    @Override
    public void scale(float scale){
        super.scale(scale);
        shadow.scale(scale);
    }
    @Override
    public void reset(){
        super.reset();
        pastXs.clear();
        pastYs.clear();
    }
    //endregion

    public void neutralHeavyAtk() {
        if (!initiateAtk(Constants.FighterConstants.kNHEAVYIndex, 0))
            return;

        if(!attackSent){
            setPosition(shadow.getX(), shadow.getY());
            pastXs.clear();
            pastYs.clear();
            shadow.beInvisible(120);
            attackSent = true;
        }
    }
}
