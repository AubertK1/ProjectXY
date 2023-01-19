package com.mygdx.game.OI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Characters.Fighter;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;

/**
 * The actual player. This is what is directly being affected by the user, and it uses inputs
 * to call the Fighter class methods.
 * The user controls the Player. the Fighter is simply the physical manifestation of the Player.
 */
public class Player {
    public Fighter fighter;
    //if this is player 1, player 2, etc.
    int playerNum;

    public Player(int playerNum) {
        this.playerNum = playerNum;
    }

    /**
     * sets this player's fighter
     * @param fighter
     */
    public void setFighter(Fighter fighter){
        this.fighter = fighter;
        fighter.setPlayer(this);
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

    /**
     * Takes in which key is being pressed and moves the fighter accordingly
     * @param KEY The key being pressed
     */
    public void interact(int KEY){
        float deltaTime = Main.getFrameRate();

        if(playerNum == 2) { //if this is player 2, convert the numbers
            switch (KEY) {
                case 20:
                    KEY = 47; //converting DOWN to S
                    break;
                case 21:
                    KEY = 29; //converting LEFT to A
                    break;
                case 22:
                    KEY = 32; //converting RIGHT to D
                    break;
                case 19:
                    KEY = 51; //converting UP to W
                    break;
            }
        }

        //registers player's input
        //key presses
        if (KEY == 32) fighter.getPosition().x += deltaTime * fighter.getSpeed(); //32 = D
        if (KEY == 29) fighter.getPosition().x -= deltaTime * fighter.getSpeed(); //29 = A
        if (KEY == 51){ //51 = W
            if(!fighter.isJumping()) {
                fighter.jump();
            }
        }
        if (KEY == 47 && fighter.canFall()) fighter.getPosition().y -= deltaTime * fighter.getSpeed(); //47 = S
    }

    /**
     * renders the fighter's model
     * @param batch just put batch
     */
    public void renderFighter(SpriteBatch batch){
        fighter.render(batch);
    }
    /**
     * same function, but you can scale the fighter to make it larger
     * @param scale how much you want to times it by
     */
    public void renderFighter(SpriteBatch batch, float scale){
        fighter.render(batch, scale);
    }
}
