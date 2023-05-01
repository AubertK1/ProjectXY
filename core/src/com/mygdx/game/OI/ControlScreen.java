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

            final TextButton upP1 =  createTextButton("W", 150, 100,100,100);
            final TextButton downP1 =   createTextButton("S",150, 1200,100,100);
            final TextButton rightP1 =  createTextButton("D",150, 140,100,100);
            final TextButton leftP1 =   createTextButton("A",150, 160,100,100);
            final TextButton lightP1 =  createTextButton("F",150, 180,100,100);
            final TextButton heavyP1 =  createTextButton("G",150, 200,100,100);
            final TextButton tempP1 =   createTextButton("Y",150, 220,100,100);
            final TextButton interactP1 =  createTextButton("E",150, 240,100,100);

            final TextButton upP2 = createTextButton("UP ARROW",350, 100,100,100);
            final TextButton downP2 = createTextButton("DOWN ARROW",350, 120,100,100);
            final TextButton rightP2 = createTextButton("RIGHT ARROW",350, 140,100,100);
            final TextButton leftP2 = createTextButton("LEFT ARROW",350, 160,100,100);
            final TextButton lightP2 =createTextButton("COMMA",350, 180,100,100);
            final TextButton heavyP2 =createTextButton("PERIOD",350, 200,100,100);
            final TextButton tempP2 = createTextButton("/",350, 220,100,100);
            final TextButton interactP2 = createTextButton("CTRL R",350, 240,100,100);

        });
    }

    public void render(SpriteBatch batch){

    }
}
