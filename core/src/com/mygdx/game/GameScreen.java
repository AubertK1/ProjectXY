package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Characters.Robot;
import com.mygdx.game.Characters.Vampire;
import com.mygdx.game.OI.Player;
import com.mygdx.game.Weapons.Sword;
import com.mygdx.game.Weapons.Weapon;

import java.awt.*;
import java.util.ArrayList;

public class GameScreen {
    private Player player1;
    private Player player2;
    private ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Weapon> weapons = new ArrayList<>();

    public Platform stage;
    private Texture background;

    //spawn points
    Vector2 spawn1 = new Vector2(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f);
    Vector2 spawn2 = new Vector2(Gdx.graphics.getWidth() / 2f + 200, Gdx.graphics.getHeight() / 2f);
    Vector2 spawnCenter = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

    Rectangle gameBounds = new Rectangle(-600, -600, Gdx.graphics.getWidth() + 1200, Gdx.graphics.getHeight() + 1200);

    //the current frame the game is on
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
        player1.setFighter(new Robot(spawn1.x, spawn1.y, player1));
        player1.getFighter().setSize(player1.getFighter().getWidth() * 3, player1.getFighter().getHeight() * 3);

        player2.setFighter(new Vampire(spawn2.x, spawn2.y, player2));
        player2.getFighter().setSize(player2.getFighter().getWidth() * 3, player2.getFighter().getHeight() * 3);

        players.add(player1);
        players.add(player2);

        Weapon sword = new Sword(Gdx.graphics.getWidth() / 2f, stage.getY() + 500);
        sword.setSize(sword.getWidth() * 2, sword.getHeight() * 2);
        weapons.add(sword);
    }
    private void update(){
        FRAMECOUNT++;

        //checking if anything goes out of bounds
        for (Player player: players) {
            if(!gameBounds.contains(player.getFighter().getX(), player.getFighter().getY())) player.getFighter().setPosition(spawnCenter.x, spawnCenter.y);
        }

        //fixme Remove this later
        for (Weapon weapon: weapons) {
            if(!gameBounds.contains(weapon.getX(), weapon.getY())) weapon.setPosition(spawnCenter.x, spawnCenter.y);
        }
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }
    public static ArrayList<Weapon> getWeapons(){
        return weapons;
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
        update();

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.render(batch);
        for (Weapon weapon: weapons) {
            weapon.render(batch);
        }
        player1.renderFighter(batch);
        player2.renderFighter(batch);

    }
}
