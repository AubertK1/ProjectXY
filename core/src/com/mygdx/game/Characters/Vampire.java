package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Constants;
import com.mygdx.game.KeyBinds;
import com.mygdx.game.OI.Player;

import java.util.LinkedList;

public class Vampire extends Fighter{
    private LinkedList<Float> pastXs = new LinkedList<>();
    private LinkedList<Float> pastYs = new LinkedList<>();
    private LinkedList<Integer> pastAnims = new LinkedList<>();

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

        runAnimation = animate(new Texture("assets\\textures\\Vampire_Bot\\Vampire_Bot_Running_Sheet.png"), 2, 3, 36);
        jumpAnimation = animate(new Texture("assets\\textures\\Vampire_Bot\\Vampire_Bot_Jumping_Sheet.png"), 1, 1, 30);

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
        pastAnims.add(currentAnimationToInt());
        if(pastXs.size() == 121){ //can go back a max of 181 frames
            pastXs.remove(); //remove the first index / the oldest x position
            pastYs.remove(); //remove the first index / the oldest y position
            pastAnims.remove();
        }
        //endregion
        shadow.setPosition(pastXs.element(), pastYs.element());
        shadow.selectAnimationFromNumber(pastAnims.element());
    }

    @Override
    public void render(SpriteBatch batch) {
        shadow.render(batch);
        super.render(batch);
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
    //region animation number storing
    private int currentAnimationToInt(){
        if (currentAnimation == runAnimation) {
            return 1;
        } else if (currentAnimation == jumpAnimation) {
            return 2;
        } else if (currentAnimation == fallAnimation) {
            return 3;
        } else if (currentAnimation == nLightAnimation) {
            return 4;
        } else if (currentAnimation == sLightAnimation) {
            return 5;
        } else if (currentAnimation == dLightAnimation) {
            return 6;
        } else if (currentAnimation == nHeavyAnimation) {
            return 7;
        } else if (currentAnimation == sHeavyAnimation) {
            return 8;
        } else if (currentAnimation == dHeavyAnimation) {
            return 9;
        } else return 0;
    }
    //endregion

    public void neutralHeavyAtk() {
        if(!shadow.getIsVisible()) return;
        if (!initiateAtk(Constants.FighterConstants.kNHEAVYIndex, 0))
            return;

        if(!attackSent){
            setPosition(shadow.getX(), shadow.getY());
            pastXs.clear();
            pastYs.clear();
            pastAnims.clear();
            shadow.beInvisible(120);
            attackSent = true;
        }
    }
}
