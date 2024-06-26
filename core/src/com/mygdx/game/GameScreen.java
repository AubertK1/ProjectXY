package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Characters.Cyborg;
import com.mygdx.game.Characters.Robot;
import com.mygdx.game.Characters.Vampire;
import com.mygdx.game.OI.Player;
import com.mygdx.game.OI.Screen;
import com.mygdx.game.Projectiles.ProjectilePool;
import com.mygdx.game.Weapons.Weapon;

import java.util.ArrayList;

/**
 * This is where the player(s) fight with their chosen character
 */
public class GameScreen extends Screen {
    private Player player1;
    private Player player2;
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Weapon> weapons = new ArrayList<>();
    private static ArrayList<Platform> platforms = new ArrayList<>();

    public Platform mainPlatform;
    private Texture background;

    public static ProjectilePool projectilePool = new ProjectilePool();

    //spawn points
    public static Vector2 spawn1 = new Vector2(Gdx.graphics.getWidth() / 2f - 200, Gdx.graphics.getHeight() / 2f);
    public static Vector2 spawn2 = new Vector2(Gdx.graphics.getWidth() / 2f + 200, Gdx.graphics.getHeight() / 2f);
    public static Vector2 spawnCenter = new Vector2(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);

    public static final Rectangle gameBounds = new Rectangle(-600, -600, Gdx.graphics.getWidth() + 1200, Gdx.graphics.getHeight() + 1200);

    //the current frame the game is on
    private static int FRAMECOUNT = 0;
    //game gravity variable. This determines how fast the characters fall
    public static float GRAVITY = -115;

    public static boolean gameOver = false;

    //region UI
    Skin skin = Main.skin;
    Stage stage = Main.stage;

    // Makes a new group
    Group UI = new Group();

    Label p1Label, p2Label;
    //endregion

    //region timerRegion (Adds a time limit to how long a match can go on for)
    Label timerLabel;
    int sec;
    int min;
    float timerTimeElapsed=0;
    final int ROUND_TIME_SEC = 0;
    final int ROUND_TIME_MIN = 10;
    Label winLabel;
    //endregion
    //region backButton
    public TextButton back;
    //endregion
    public GameScreen() {
        // initializing everything
        background = new Texture("assets\\textures\\Background\\Night_Time_Background.png");

        Texture mainPTex = new Texture("assets\\textures\\Platforms\\Floating_Platform.png");
        mainPlatform = new Platform((Gdx.graphics.getWidth() / 2f) - (mainPTex.getWidth() / 2f),
                (Gdx.graphics.getHeight() * .4f) - (mainPTex.getHeight()), mainPTex);
        mainPlatform.setHurtbox(43, 35, 122, 45);
        mainPlatform.scale(8);
        mainPlatform.setPositionFromHB((Gdx.graphics.getWidth() / 2f) - (mainPlatform.getHBWidth() / 2f),
                (Gdx.graphics.getHeight() * .26f) - (mainPlatform.getHBHeight() / 2f));
        platforms.add(mainPlatform);

//        Platform platform2 = new Platform(200, 600, new Texture("assets\\textures\\Platforms\\Neon_Platform_Sheet.png"), 5, 2);
//        platform2.setHurtbox(39, 8, 135, 89);
//        platform2.scale(2);
//        platforms.add(platform2);

        player1 = new Player(1);
        player2 = new Player(2);
        // setting player 1's fighter (will be moved later so the player can choose)
        player1.setFighter(new Cyborg(spawn1.x, spawn1.y, player1));
        player1.getFighter().scale(3);

        player2.setFighter(new Robot(spawn2.x, spawn2.y, player2));
        player2.getFighter().scale(3);

        players.add(player1);
        players.add(player2);

/*
        //fixme Add this back later if needed
        Weapon sword = new Sword(spawnCenter.x, spawnCenter.y);
        sword.scale(2);
        weapons.add(sword);
*/

        //region screen UI
        UI.setPosition(100, 50);

        p1Label = new Label("", skin);
        p1Label.setSize(100, 50);
        p1Label.setPosition(0, 0);

        p2Label = new Label("", skin);
        p2Label.setSize(p1Label.getWidth(), p1Label.getHeight());
        p2Label.setPosition(p1Label.getX() + p1Label.getWidth() + 5, p1Label.getY());

        UI.addActor(p1Label);
        UI.addActor(p2Label);
        //endregion

        //region timerRegion (Displays timer counting down on GameScreen)
        sec=ROUND_TIME_SEC;
        min=ROUND_TIME_MIN;

        timerLabel = new Label("Timer: " + min +":"+getSecString(), skin);
        timerLabel.setSize(100, 50);
        timerLabel.setPosition(1700, 900);

        winLabel = new Label("" , skin);
        winLabel.setSize(500, 200);
        winLabel.setPosition(800, 600);

        UI.addActor(timerLabel);
        UI.addActor(winLabel);
        //endregion

        //region backButton (Adds a button that allows the user to go back to the Main Menu Screen)\
         back = createTextButton("BACK",-100,850,200,100);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                min = ROUND_TIME_MIN;
                sec=ROUND_TIME_SEC;
                gameOver = false;
                winLabel.setText("");
                Main.changeScreen("MainMenu");
            }
        });
        //endregion
    }

    /**
     * updates the system's frames and declares a winner based on which player won
     */
    private void update(){
        FRAMECOUNT++;

        projectilePool.update();

        //checking if anything goes out of bounds
        for (Player player: players) {
            if(!gameBounds.contains(player.getFighter().getX(), player.getFighter().getY())) player.resetAssets();
        }

/*
        //fixme Add this back later if needed
        for (Weapon weapon: weapons) {
            if(!gameBounds.contains(weapon.getX(), weapon.getY())) weapon.setPosition(spawnCenter.x, spawnCenter.y);
        }
*/

        //region UI ( shows the fight's health)
        p1Label.setText("ROBOT: " + player1.getFighter().getHealth());
        p2Label.setText("VAMPIRE: " + player2.getFighter().getHealth());
        //endregion

        //region Deaths  (States who wins based on if the lives run out)
        if (players.get(0).getFighter().getLives() ==0){
            winLabel.setText("player2 wins");
            gameOver = true;
        } else if (players.get(1).getFighter().getLives()==0){
            winLabel.setText("player1 wins");
            gameOver = true;
        }
        //endregion

        //region Timer and Wins (Sets the results of the match if the timer runs out with conditions)
        timerLabel.setText("Timer: " + min +":"+getSecString());
       timerTimeElapsed = timerTimeElapsed+Gdx.graphics.getDeltaTime();
       if (timerTimeElapsed>=1) {
           decreaseSec();
           if (min == 0) {
               if (sec == 0) {
                   if(players.get(0).getFighter().getLives() > players.get(1).getFighter().getLives()) {
                       winLabel.setText("Player1 wins");
                   }else  if(players.get(1).getFighter().getLives() > players.get(0).getFighter().getLives()) {
                       winLabel.setText("Player2 wins");
                   }
                   else  if(players.get(0).getFighter().getLives() == players.get(1).getFighter().getLives()) {
                       if (players.get(0).getFighter().getHealth()/players.get(0).getFighter().maxHealth > players.get(1).getFighter().getHealth()/players.get(1).getFighter().maxHealth){
                           winLabel.setText("Player1 Wins");
                       }
                       else if (players.get(1).getFighter().getHealth()/players.get(1).getFighter().maxHealth > players.get(0).getFighter().getHealth()/players.get(0).getFighter().maxHealth) {
                           winLabel.setText("Player2 Wins");
                       }

                   }
                   else  {
                       winLabel.setText("tie");
                   }

                 gameOver = true;
               }
           }
       }
        //endregion

        p1Label.setText(player1.getFighter().getName() + ": " + player1.getFighter().getHealth());
        p2Label.setText(player2.getFighter().getName() + ": " + player2.getFighter().getHealth());
    }

    public static ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     *  Places the character the user(s) chose and puts it on the screen
     * @param player the user
     * @param fighter the character the user chose
     */
    public static void setPlayers(int player,String fighter){
        Vector2 spawn;
        if (player == 1){
            spawn = spawn1;
        }else {
            spawn = spawn2;
        }

        if(fighter.equals("Vampire")){
            players.get(player-1).setFighter(new Vampire(spawn.x, spawn.y,  players.get(player-1)));
        }else if (fighter.equals("Cyborg")){
            players.get(player-1).setFighter(new Cyborg(spawn.x, spawn.y,  players.get(player-1)));
        }else{
            players.get(player-1).setFighter(new Robot(spawn.x, spawn.y,  players.get(player-1)));
        }

    }
    public static ArrayList<Weapon> getWeapons(){
        return weapons;
    }
    public static ArrayList<Platform> getPlatforms(){
        return platforms;
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
//        mainPlatform.render(batch);
        for (Platform platform: platforms) {
            platform.render(batch);
        }
        for (Weapon weapon: weapons) {
            if(weapon.getOwner() == null) weapon.render(batch);
        }
        player1.renderAssets(batch);
        player2.renderAssets(batch);

        projectilePool.renderProjectiles(batch);
    }

    //region Timer (makes the timer countdown)
    /**
     * makes the time go down
     */
    private void decreaseSec(){
        sec=sec-1;
        if (sec<0){
            min=min-1;
            sec=59;
        }
        timerTimeElapsed= timerTimeElapsed-1;
    }

    /**
     * Puts a 0 in front of single digit numbers
     * @return
     */
    private String getSecString(){
        if(sec<10){
            return "0"+sec;
        }
        return ""+sec;
    }
    //endregion

    /**
     *  Adds a textbutton to the screen
     * @param buttonText
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public TextButton createTextButton(String buttonText,int x, int y, int width, int height){
        final TextButton aTextbutton = new TextButton(buttonText,skin);
        aTextbutton.setPosition(x,y);
        aTextbutton.setSize(width,height);
        UI.addActor(aTextbutton);
        return aTextbutton;
    }

}
