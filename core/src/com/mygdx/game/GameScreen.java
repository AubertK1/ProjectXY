package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Weapon> weapons = new ArrayList<>();

    public Platform platform;
    private Texture background;

    //spawn points
    public static Vector2 spawn1 = new Vector2(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f);
    public static Vector2 spawn2 = new Vector2(Gdx.graphics.getWidth() / 2f + 200, Gdx.graphics.getHeight() / 2f);
    public static Vector2 spawnCenter = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

    Rectangle gameBounds = new Rectangle(-600, -600, Gdx.graphics.getWidth() + 1200, Gdx.graphics.getHeight() + 1200);

    //the current frame the game is on
    private static int FRAMECOUNT = 0;
    //game gravity variable. This determines how fast the characters fall
    public static float GRAVITY = -115;

    //region UI
    Skin skin = Main.skin;
    Stage stage = Main.stage;
    // Makes a new group
    Group UI = new Group();

    Label p1Label, p2Label;
    //endregion
    public GameScreen() {
        // initializing everything
        background = new Texture("assets\\textures\\Night_Time_Background.png");
        platform = new Platform(new Texture("assets\\textures\\Floating_Platform.png"));
        platform.sizeToWidth(900);
        platform.setPosition((Gdx.graphics.getWidth() / 2f) - (platform.getWidth() / 2f),
                (Gdx.graphics.getHeight() * .4f) - (platform.getHeight()));
        player1 = new Player(1);
        player2 = new Player(2);
        // setting player 1's fighter (will be moved later so the player can choose)
        player1.setFighter(new Robot(spawn1.x, spawn1.y, player1));
        player1.getFighter().setSize(player1.getFighter().getWidth() * 2.5f, player1.getFighter().getHeight() * 2.5f);

        player2.setFighter(new Vampire(spawn2.x, spawn2.y, player2));
        player2.getFighter().setSize(player2.getFighter().getWidth() * 2.5f, player2.getFighter().getHeight() * 2.5f);

        players.add(player1);
        players.add(player2);

        Weapon sword = new Sword(Gdx.graphics.getWidth() / 2f, platform.getY() + 500);
        sword.setSize(sword.getWidth() * 2, sword.getHeight() * 2);
        weapons.add(sword);

        //region screen UI
        UI.setPosition(100, 50);

        p1Label = new Label("ROBOT: " + player1.getFighter().getHealth(), skin);
        p1Label.setSize(100, 50);
        p1Label.setPosition(0, 0);

        p2Label = new Label("VAMPIRE: " + player2.getFighter().getHealth(), skin);
        p2Label.setSize(p1Label.getWidth(), p1Label.getHeight());
        p2Label.setPosition(p1Label.getX() + p1Label.getWidth() + 5, p1Label.getY());

        UI.addActor(p1Label);
        UI.addActor(p2Label);
        //endregion
    }
    private void update(){
        FRAMECOUNT++;

        //checking if anything goes out of bounds
        for (Player player: players) {
            if(!gameBounds.contains(player.getFighter().getX(), player.getFighter().getY())) player.resetAssets();
        }

        //fixme Remove this later
        for (Weapon weapon: weapons) {
            if(!gameBounds.contains(weapon.getX(), weapon.getY())) weapon.setPosition(spawnCenter.x, spawnCenter.y);
        }

        p1Label.setText("ROBOT: " + player1.getFighter().getHealth());
        p2Label.setText("VAMPIRE: " + player2.getFighter().getHealth());
    }

    public static ArrayList<Player> getPlayers(){
        return players;
    }
    public static ArrayList<Weapon> getWeapons(){
        return weapons;
    }
    public static int getFrame(){
        return FRAMECOUNT;
    }
    public void startDrawing(){
        stage.addActor(UI);
    }
    public void stopDrawing(){
        UI.remove();
    }
    /**
     * renders everything on the screen
     * 
     * @param batch just put batch
     */
    public void render(SpriteBatch batch){
        update();

        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        platform.render(batch);
        for (Weapon weapon: weapons) {
            if(weapon.getOwner() == null) weapon.render(batch);
        }
        player1.renderAssets(batch);
        player2.renderAssets(batch);
    }
}
