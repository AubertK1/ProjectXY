package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;

public class ControlScreen extends MenuScreen {
    public ControlScreen() {


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
            /*
            final TextButton upP1 = new TextButton("W",);

            final TextButton downP1 = new TextButton("S",);

            final TextButton rightP1 = new TextButton("D",);

            final TextButton leftP1 = new TextButton("A");

            final TextButton lightP1 = new TextButton("F",);

            final TextButton heavyP1 = new TextButton("G",);

            final TextButton tempP1 = new TextButton("Y",);

            final TextButton interactP1 = new TextButton("E",);

            final TextButton upP2 = new TextButton("UP ARROW",);
            final TextButton downP2 = new TextButton("DOWN ARROW",);
            final TextButton rightP2 = new TextButton("RIGHT ARROW",);
            final TextButton leftP2 = new TextButton("LEFT ARROW");
            final TextButton lightP2 = new TextButton("COMMA",);
            final TextButton heavyP2 = new TextButton("PERIOD",);
            final TextButton tempP2 = new TextButton("/",);
            final TextButton interactP2 = new TextButton("CTRL R",);
        */
        });
    }

    public void render(SpriteBatch batch){

    }
}
