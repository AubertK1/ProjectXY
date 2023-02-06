package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Characters.Robot;
import com.mygdx.game.Characters.Vampire;
import com.mygdx.game.OI.Player;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GameScreen {
    private Player player1;
    private Player player2;
    private ArrayList<Player> players = new ArrayList<>();
    public Platform stage;
    Texture background;
    private static int FRAMECOUNT = 0;
    //game gravity variable. This determines how fast the characters fall
    public static float GRAVITY = -115;
    public GameScreen() {
        // initializing everything
        background = new Texture("assets\\textures\\stockbg.jpg");
        stage = new Platform(new Texture("assets\\textures\\stockstage.png"));
        player1 = new Player(1);
        player2 = new Player(2);
        // setting player 1's fighter (will be moved later so the player can choose)
        player1.setFighter(new Robot(Gdx.graphics.getWidth() / 2f - 100, Gdx.graphics.getHeight() / 2f, player1));
        player1.getFighter().setSize(player1.getFighter().getWidth() * 3, player1.getFighter().getHeight() * 3);

        player2.setFighter(new Vampire(Gdx.graphics.getWidth()/2f+100, Gdx.graphics.getHeight()/2f, player2));
        player2.getFighter().setSize(player2.getFighter().getWidth() * 3, player2.getFighter().getHeight() * 3);

        players.add(player1);
        players.add(player2);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }
    public static int getFrame(){
        return FRAMECOUNT;
    }
    /**
     * renders everything on the screen
     * 
     * @param batch just put batch
     */
    public void render(SpriteBatch batch){
        FRAMECOUNT++;
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(stage.model, (Gdx.graphics.getWidth() / 2f) - (stage.model.getWidth() / 2f),
                (Gdx.graphics.getHeight() * .4f) - (stage.model.getHeight()));
        player1.renderFighter(batch);
        player2.renderFighter(batch);
    }
}
