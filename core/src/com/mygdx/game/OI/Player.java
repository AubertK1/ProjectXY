package com.mygdx.game.OI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.Characters.Fighter;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;
import com.mygdx.game.Object;
import com.mygdx.game.Weapons.Weapon;

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
        fighter.update();

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

    //region interactions
    public void equipWeapon(Weapon weapon){
        equippedWeapon = weapon;
        weapon.setOwner(this);
//        weapon.setIsCollidable(false);
    }
    public void throwWeapon(){
        equippedWeapon.setOwner(null); //so that the weapon doesn't stick to the fighter anymore

        float launchXVelo = -500;
        if(fighter.getXVelocity() > 0) launchXVelo = 0 - launchXVelo; //switches the throw from right to left based on the fighter's movement
        equippedWeapon.launch(launchXVelo + (fighter.getXVelocity() * 2), 1700); //throws further if the fighter's moving

        equippedWeapon = null;
    }
    //endregion


    /**
     * Takes in which key is being pressed and moves the fighter accordingly
     * @param KEY The key being pressed
     */
    public void interact(int KEY){
        if(playerNum == 2) { //if this is player 2, convert the numbers
            switch (KEY) {
                case Input.Keys.DOWN:
                    KEY = Input.Keys.S; //converting DOWN to S
                    break;
                case Input.Keys.LEFT:
                    KEY = Input.Keys.A; //converting LEFT to A
                    break;
                case Input.Keys.RIGHT:
                    KEY = Input.Keys.D; //converting RIGHT to D
                    break;
                case Input.Keys.UP:
                    KEY = Input.Keys.W; //converting UP to W
                    break;
                case Input.Keys.SLASH:
                    KEY = Input.Keys.F;
                    break;
            }
        }

        //registers player's input
        //key presses
        if (KEY == Input.Keys.D) {
            if (fighter.isColliding(Main.gameScreen.stage) == Object.RIGHTCOLLISION) fighter.stop(); //32 = D
            else fighter.moveRight();
        }
        if (KEY == Input.Keys.A) {
            if (fighter.isColliding(Main.gameScreen.stage) == Object.LEFTCOLLISION) fighter.stop(); //29 = A
            else fighter.moveLeft();
        }
        if (KEY == Input.Keys.W){ //51 = W
            if(!fighter.isJumping()) fighter.jump();
        }
        if (KEY == Input.Keys.S && fighter.canFall()) fighter.moveDown(); //47 = S
        if (KEY == Input.Keys.F){
            if(equippedWeapon == null) {
                Weapon interactedWeapon = null;
                for (Weapon weapon : GameScreen.getWeapons()) {
                    if (fighter.isColliding(weapon) != Object.NOCOLLISION) interactedWeapon = weapon;
                }
                if (interactedWeapon != null) equipWeapon(interactedWeapon); //47 = S
            }
            else {
                throwWeapon();
            }
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
    public void renderFighter(SpriteBatch batch){
        fighter.render(batch);
    }
}
