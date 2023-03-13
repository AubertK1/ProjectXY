package com.mygdx.game.OI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.*;
import com.mygdx.game.Characters.Fighter;
import com.mygdx.game.Object;
import com.mygdx.game.Weapons.Weapon;

import java.awt.*;

/**
 * The actual player. This is what is directly being affected by the user, and it uses inputs
 * to call the Fighter class methods.
 * The user controls the Player. the Fighter is simply the physical manifestation of the Player.
 */
public class Player {
    private Fighter fighter;
    //if this is player 1, player 2, etc.
    int playerNum;

    private Weapon equippedWeapon;

    public Player(int playerNum) {
        this.playerNum = playerNum;
    }

    /**
     * updates the visuals or anything that needs to be updated every frame
     */
    public void update() {
        continueAttack();

        //registers player's input for all inputs that can be held
        if(playerNum == 1) { //if player 1...
            if (Gdx.input.isKeyPressed(Input.Keys.D)) interact(Input.Keys.D);
            if (Gdx.input.isKeyPressed(Input.Keys.A)) interact(Input.Keys.A);
            if (Gdx.input.isKeyPressed(Input.Keys.S) && fighter.canFall()) interact(Input.Keys.S);
        }
        else if(playerNum == 2) { //if player 2...
            // keypresses
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) interact(Input.Keys.RIGHT);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) interact(Input.Keys.LEFT);
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && fighter.canFall()) interact(Input.Keys.DOWN);
        }
    }

    public void resetAssets(){
        fighter.reset();
        if (equippedWeapon != null){
            Weapon weapon = equippedWeapon;
            throwWeapon();
            weapon.spawn(); //fixme remove later when weapon spawning is improved
        }
    }

    //region interactions
    public void equipWeapon(Weapon weapon){
        equippedWeapon = weapon;
        weapon.setOwner(this);
    }
    public void throwWeapon(){
        equippedWeapon.setOwner(null); //so that the weapon doesn't stick to the fighter anymore

        float launchXVelo = -500;
        if(fighter.getXVelocity() > 0) launchXVelo = 0 - launchXVelo; //switches the throw from right to left based on the fighter's movement
        equippedWeapon.launch(launchXVelo + (fighter.getXVelocity() * 2), 1700); //throws further if the fighter's moving

        equippedWeapon = null;
    }
    public Player checkHit(){
        if(equippedWeapon == null) {
            for (Player player2 : GameScreen.getPlayers()) {
                if(player2 == this) continue;
                if (Object.isColliding(getFighter().getHitboxBounds(), player2.getFighter().getHurtboxBounds()) != Object.NOCOLLISION) {
                    return player2;
                }
                else if(player2 == GameScreen.getPlayers().get(GameScreen.getPlayers().size() - 1)){
                    return null;
                }
            }
        }
        return null;
    }
    public void continueAttack(){
        switch (fighter.getCurrentATK()){
            case NLIGHT: fighter.neutralLightAtk();
                break;
            case SLIGHT: fighter.sideLightAtk();
                break;
            case DLIGHT: fighter.downLightAtk();
                break;
        }
    }
    public void startAttack(){
        int directionKey = -10;
        //region finding which direction to attack
        if(playerNum == 1) { //if player 1...
            if (Gdx.input.isKeyPressed(Input.Keys.D)) directionKey = KeyBinds.convertKey(Input.Keys.D);
            else if (Gdx.input.isKeyPressed(Input.Keys.A)) directionKey = KeyBinds.convertKey(Input.Keys.A);
            else if (Gdx.input.isKeyPressed(Input.Keys.S)) directionKey = KeyBinds.convertKey(Input.Keys.S);
        }
        else if(playerNum == 2) { //if player 2...
            // keypresses
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) directionKey = KeyBinds.convertKey(Input.Keys.RIGHT);
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) directionKey = KeyBinds.convertKey(Input.Keys.LEFT);
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) directionKey = KeyBinds.convertKey(Input.Keys.DOWN);
        }
        //endregion
        //region attacking in the direction
        switch (directionKey) {
            case (-10): //if no direction
                fighter.neutralLightAtk();
                break;
            case (KeyBinds.Keys.RIGHT):
            case (KeyBinds.Keys.LEFT):
                fighter.sideLightAtk();
                break;
            case (KeyBinds.Keys.DOWN):
                fighter.downLightAtk();
                break;
        }
        //endregion
    }
    public void strike(Player struckPlayer, HitData fighterHitData){
        float avgKBMultiplier = fighterHitData.knockbackMultiplier;
        if (equippedWeapon != null)
            avgKBMultiplier = (fighterHitData.knockbackMultiplier);
        HitData attackData = new HitData().set(fighterHitData.damage,
                HitData.IGNORE, avgKBMultiplier, fighterHitData.direction, fighterHitData.hitStunDuration);

        struckPlayer.takeDamage(attackData, fighter.isFacingRight());
    }
    public void takeDamage(HitData hitData, boolean preferRight){
        fighter.takeDamage(hitData.damage);
        fighter.knockBack(hitData.direction, hitData.knockbackMultiplier, preferRight);
        fighter.stun(hitData.hitStunDuration);
    }
    public void pull(Player pulledPlayer, Point point, float time){
        pulledPlayer.getFighter().pullTo(point, time);
    }
    //endregion

    /**
     * Takes in which key is being pressed and moves the fighter accordingly
     * @param PRESSEDKEY The key being pressed
     */
    public void interact(int PRESSEDKEY){
        //converts the key into its value in the default keyset
        int KEY = KeyBinds.convertKey(PRESSEDKEY);

        //registers player's input
        //key presses
        switch (KEY){
            case (KeyBinds.Keys.RIGHT):
                if (fighter.isCollidingWith(Main.gameScreen.mainPlatform) == Object.RIGHTCOLLISION) fighter.stop();
                else if(fighter.getXVelocity() < 0) fighter.stop();
                else if (fighter.isAttacking());
                else fighter.moveRight();
                break;
            case (KeyBinds.Keys.LEFT):
                if (fighter.isCollidingWith(Main.gameScreen.mainPlatform) == Object.LEFTCOLLISION) fighter.stop();
                else if(fighter.getXVelocity() > 0) fighter.stop();
                else if (fighter.isAttacking());
                else fighter.moveLeft();
                break;
            case (KeyBinds.Keys.JUMP):
                if (fighter.isAttacking());
                else if(!fighter.isJumping()) fighter.jump();
                break;
            case (KeyBinds.Keys.DOWN):
                if(fighter.canFall()) fighter.moveDown();
                break;
            case (KeyBinds.Keys.INTERACT):
                if(equippedWeapon == null) {
                    Weapon interactedWeapon = null;
                    for (Weapon weapon : GameScreen.getWeapons()) {
                        if (fighter.isCollidingWith(weapon) != Object.NOCOLLISION && weapon.getOwner() == null){
                            interactedWeapon = weapon;
                            break;
                        }
                    }
                    if (interactedWeapon != null) equipWeapon(interactedWeapon);
                }
                else {
                    throwWeapon();
                }
                break;
            case (KeyBinds.Keys.ATTACK):
                startAttack();
                break;
            case (KeyBinds.Keys.TEMP): //fixme testing keybind changing. Delete later
                KeyBinds.changeKeyBind(KeyBinds.findKeyFromDefaultKey(KeyBinds.Keys.JUMP, playerNum - 1), Input.Keys.SPACE);
                break;
        }
    }
    /**
     * sets this player's fighter
     * @param fighter
     */
    public void setFighter(Fighter fighter){
        this.fighter = fighter;
        fighter.setPlayer(this);
    }
    public Fighter getFighter(){
        return fighter;
    }

    /**
     * renders the fighter's model
     * @param batch just put batch
     */
    public void renderAssets(SpriteBatch batch){
        update();

        fighter.render(batch);
        if(equippedWeapon != null) equippedWeapon.render(batch);
    }
}
