package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;

public class MainMenu extends MenuScreen {

    public MainMenu() {

        background = new Texture("assets\\textures\\Title_Screen.png");

        final Button play = createImageButton("textures/Start_Button.png",700,80,600,400);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.changeScreen("P1CharacterSelectScreen");
            }
        });

        final Button controls = createImageButton("textures/settings_image.png",25,325,200,200);

        controls.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.changeScreen("ControlScreen");

            }
        });

    }


}
