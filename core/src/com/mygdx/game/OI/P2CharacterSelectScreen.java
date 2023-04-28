package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.GameScreen;
import com.mygdx.game.Main;

public class P2CharacterSelectScreen extends MenuScreen{
    public Texture characterSelectImage = new Texture("textures/CHOOSE_YOUR_CHARACTER.png");
    public Texture player2Image = new Texture("textures/P2.png");

    public Button vampireImage = createImageButton("textures/Vampire_Bot/Vampire_Bot_48x.png",250,400,400,300);
    public Button violetCyborgImage = createImageButton("textures/Violet_Cyborg/Violet_Cyborg_48x.png",750,400,300,300);
    public Button securityRobotImage = createImageButton("textures/Security_Robot/Security_Robot_48x.png",1250,400,300,300);
    public P2CharacterSelectScreen() {
        background = new Texture("assets\\textures\\Night_Time_Background.png");

        final Button fight = createImageButton("textures/FIGHT.PNG", 25, 550, 200, 200);
        fight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Main.changeScreen("GameScreen");
            }
        });
        final TextButton play = createTextButton("BACK",25,200,200,200);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.changeScreen("P1CharacterSelectScreen");
            }
        });

        vampireImage.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                GameScreen.setPlayers(2,"Vampire");
            }
        });
        violetCyborgImage.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                GameScreen.setPlayers(2,"Cyborg");
            }
        });
        securityRobotImage.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y) {
                GameScreen.setPlayers(2,"Robot");
            }
        });
    }


    public void render(SpriteBatch batch){
        batch.draw(background,0, 0,2000,1000);
        batch.draw(characterSelectImage,750, 800, 400, 200);
        batch.draw(player2Image,1150, 850, 200, 100);
    }






}
