package com.mygdx.game.OI;

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
        if (KeyBinds.isKeyPressed(KeyBinds.Keys.DOWN, playerNum - 1))
            interact(KeyBinds.findKeyFromDefaultKey(KeyBinds.Keys.DOWN, playerNum - 1));
        else if (KeyBinds.isKeyPressed(KeyBinds.Keys.LEFT, playerNum - 1))
            interact(KeyBinds.findKeyFromDefaultKey(KeyBinds.Keys.LEFT, playerNum - 1));
        else if (KeyBinds.isKeyPressed(KeyBinds.Keys.RIGHT, playerNum - 1))
            interact(KeyBinds.findKeyFromDefaultKey(KeyBinds.Keys.RIGHT, playerNum - 1));
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
                if (!Object.isColliding(getFighter().getHitboxBounds(), player2.getFighter().getHurtboxBounds())[Object.NOCOLLISION]) {
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
            case NOATTACK: return;
            case NLIGHT: fighter.neutralLightAtk();
                break;
            case SLIGHT: fighter.sideLightAtk();
                break;
            case DLIGHT: fighter.downLightAtk();
                break;
            case NHEAVY: fighter.neutralHeavyAtk();
                break;
            case SHEAVY: fighter.sideHeavyAtk();
                break;
            case DHEAVY: fighter.downHeavyAtk();
                break;
        }
    }
    public void startAttack(boolean isHeavyAtk){
        if(GameScreen.getFrame() < fighter.getNextATKFrame() || fighter.isAttacking()) return;
        fighter.endAttack(0);

        int directionKey = -10;
        //region finding which direction to attack
        if (KeyBinds.isKeyPressed(KeyBinds.Keys.DOWN, playerNum - 1)) directionKey = KeyBinds.Keys.DOWN;
        else if (KeyBinds.isKeyPressed(KeyBinds.Keys.JUMP, playerNum - 1)) directionKey = -10;
        else if (KeyBinds.isKeyPressed(KeyBinds.Keys.LEFT, playerNum - 1)) directionKey = KeyBinds.Keys.LEFT;
        else if (KeyBinds.isKeyPressed(KeyBinds.Keys.RIGHT, playerNum - 1)) directionKey = KeyBinds.Keys.RIGHT;
        //endregion
        //region attacking in the direction
        if(!isHeavyAtk) { //if a light attack
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
        }
        else { //if a heavy attack
            switch (directionKey) {
                case (-10): //if no direction
                    fighter.neutralHeavyAtk();
                    break;
                case (KeyBinds.Keys.RIGHT):
                case (KeyBinds.Keys.LEFT):
                    fighter.sideHeavyAtk();
                    break;
                case (KeyBinds.Keys.DOWN):
                    fighter.downHeavyAtk();
                    break;
            }
        }
        //endregion
    }
    public void strike(Player struckPlayer, HitData fighterHitData){
        float avgKBMultiplier = fighterHitData.knockbackMultiplier;
        if (equippedWeapon != null)
            avgKBMultiplier = (fighterHitData.knockbackMultiplier);
        HitData attackData = new HitData().set(fighterHitData.damage,
                HitData.IGNORE, avgKBMultiplier, fighterHitData.direction, fighterHitData.hitStunDuration);

        struckPlayer.getStruck(attackData, fighter.isFacingRight());
    }

    public void getStruck(HitData hitData, boolean preferRight){
        fighter.getStunned(hitData.hitStunDuration);
        fighter.knockBack(hitData.direction, hitData.knockbackMultiplier, preferRight);
        fighter.takeDamage(hitData.damage);
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
        if(fighter.isStunned()) return;
        
        //converts the key into its value in the default keyset
        int KEY = KeyBinds.convertKey(PRESSEDKEY);

        //registers player's input
        //key presses
        switch (KEY){
            case (KeyBinds.Keys.RIGHT):
                if (fighter.isCollidingWith(Main.gameScreen.mainPlatform)[Object.RIGHTCOLLISION]) fighter.stop();
                else if(fighter.getXVelocity() < 0) fighter.stop();
                else fighter.moveRight();
                break;
            case (KeyBinds.Keys.LEFT):
                if (fighter.isCollidingWith(Main.gameScreen.mainPlatform)[Object.LEFTCOLLISION]) fighter.stop();
                else if(fighter.getXVelocity() > 0) fighter.stop();
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
                        if (!fighter.isCollidingWith(weapon)[Object.NOCOLLISION] && weapon.getOwner() == null){
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
            case (KeyBinds.Keys.LIGHTATTACK):
                startAttack(false);
                break;
            case (KeyBinds.Keys.HEAVYATTACK):
                startAttack(true);
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
        //grabbing any necessary initializing variables
        float scale = 1;
        if(this.fighter != null){
            scale = this.fighter.getScale();
        }

        this.fighter = fighter;

        fighter.setPlayer(this);
        //setting the initializing variables
        fighter.scale(scale);
    }
    public Fighter getFighter(){
        return fighter;
    }
    public int getPlayerNum(){
        return playerNum;
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
