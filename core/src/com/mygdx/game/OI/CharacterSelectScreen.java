package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;

public class CharacterSelectScreen extends MenuScreen{


    public Texture characterSelectImage = new Texture("textures/CHOOSE_YOUR_CHARACTER.png");

    public Texture vampireImage = new Texture("textures/Vampire_Bot/Vampire_Bot_48x.png");
    public Texture violentCyborgImage = new Texture("textures/Violet_Cyborg/Violet_Cyborg_48x.png");
    public Texture securityRobotImage = new Texture("textures/Security_Robot/Security_Robot_48x.png");

    public CharacterSelectScreen() {
        background = new Texture("assets\\textures\\Night_Time_Background.png");

        final ImageButton fight = createImageButton("textures/FIGHT.PNG", 25, 550, 200, 200);
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
                Main.changeScreen("MainMenu");
            }
        });
    }


    public void render(SpriteBatch batch){
        batch.draw(background,0, 0,2000,1000);
        batch.draw(characterSelectImage,750, 800, 400, 200);
        batch.draw(vampireImage,250,400,400,300);
        batch.draw(violentCyborgImage,750,400,300,300);
        batch.draw(securityRobotImage,1250,400,300,300);
    }

    }

