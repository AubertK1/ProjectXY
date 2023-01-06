package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen {
    Player player1;
    Player player2;
    Platform stage;
    Texture background;
    public GameScreen() {
        //initializing everything
        background = new Texture("assets\\textures\\stockbg.jpg");
        stage = new Platform(new Texture("assets\\textures\\stockstage.png"));
        player1 = new Player(1);
        player2 = new Player(2);
        //setting player 1's fighter (will be moved later so the player can choose)
        player1.setFighter(new Robot(player1));
        //setting player 1's fighter's spawn/starting position
        player1.fighter.setPosition(Gdx.graphics.getWidth()/2f-100, Gdx.graphics.getHeight()/2f);

        player2.setFighter(new Vampire(player2));
        player2.fighter.setPosition(Gdx.graphics.getWidth()/2f+100, Gdx.graphics.getHeight()/2f);
    }

    /**
     * renders everything on the screen
     * @param batch just put batch
     */
    public void render(SpriteBatch batch){
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(stage.model, (Gdx.graphics.getWidth()/2f)-(stage.model.getWidth()/2f), (Gdx.graphics.getHeight()*.4f)-(stage.model.getHeight()));
        player1.renderFighter(batch, 3);
        player2.renderFighter(batch, 3);
    }
}
