package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;

public class ControlScreen extends MenuScreen{
    public ControlScreen() {

        background = new Texture("assets\\textures\\Temp_Control_Screen.png");

        final TextButton play = createTextButton("BACK",25,550,200,200);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               Main.changeScreen("MainMenu");
            }
        });

        final TextButton controls = createTextButton("DEFAULT", 25, 325, 200, 200);

        controls.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        final TextButton changeControls = createTextButton("CHANGE KEYBINDS", 25, 100, 200, 200);

        controls.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    public void render(SpriteBatch batch){

    }
}
