package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;
/**
 If the user presses the "Play" button, it will take them to the Player1 Character Selection screen
 If the user presses the "Controls" button, it will take them to the Control Screen, where they could see their keybinds or change them if needed
 */
public class MainMenu extends MenuScreen {

    public MainMenu() {

        background = new Texture("assets\\textures\\Background\\Title_Screen.png");

        final Button play = createImageButton("textures/Button/Start_Button.png",700,80,600,400);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.changeScreen("P1CharacterSelectScreen");
            }
        });

        final Button controls = createImageButton("textures/Button/settings_image.png",25,325,200,200);

        controls.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.changeScreen("ControlScreen");

            }
        });

    }


}
