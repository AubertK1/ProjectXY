package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;

public class ControlScreen extends MenuScreen {
    public Texture player1Image = new Texture("textures/Background/P1.png");
    public Texture player2Image = new Texture("textures/Background/P2.png");

    public Label jumpLabel;
    public Label downLabel;
    public Label rightLabel;
    public Label leftLabel;
    public Label lightLabel;
    public Label heavyLabel;
    public Label tempLabel;
    public Label interactLabel;
    public ControlScreen() {
        jumpLabel = createLabel("Jump",450,750,150,100);
        downLabel = createLabel("Down",450,650,150,100);
        rightLabel = createLabel("Right",450,550,150,100);
        leftLabel  = createLabel("Left",450,450,150,100);
        lightLabel = createLabel("Light",450,350,150,100);
        heavyLabel = createLabel("Heavy",450,250,150,100);
        tempLabel = createLabel("Temp",450,  150,150,100);
        interactLabel = createLabel("Interact",450,50,100,75);


        final TextButton play = createTextButton("BACK",25,550,200,200);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.changeScreen("MainMenu");
            }
        });

            final TextButton upP1 =  createTextButton("W", 550, 750,100,75);
            final TextButton downP1 =   createTextButton("S",550, 650,100,75);
            final TextButton rightP1 =  createTextButton("D",550, 550,100,75);
            final TextButton leftP1 =   createTextButton("A",550, 450,100,75);
            final TextButton lightP1 =  createTextButton("F",550, 350,100,75);
            final TextButton heavyP1 =  createTextButton("G",550, 250,100,75);
            final TextButton tempP1 =   createTextButton("Y",550, 150,100,75);
            final TextButton interactP1 =  createTextButton("E",550, 50,100,75);

            final TextButton upP2 = createTextButton("UP ARROW",1050, 750,100,75);
            final TextButton downP2 = createTextButton("DOWN ARROW",1050, 650,100,75);
            final TextButton rightP2 = createTextButton("RIGHT ARROW",1050, 550,100,75);
            final TextButton leftP2 = createTextButton("LEFT ARROW",1050, 450,100,75);
            final TextButton lightP2 =createTextButton("COMMA",1050, 350,100,75);
            final TextButton heavyP2 =createTextButton("PERIOD",1050, 250,100,75);
            final TextButton tempP2 = createTextButton("/",1050, 150,100,75);
            final TextButton interactP2 = createTextButton("CTRL R",1050, 50,100,75);

        }

    public void render(SpriteBatch batch){
        //batch.draw(background,0, 0,2000,1000);
        batch.draw(player1Image,525,850, 200,100);
        batch.draw(player2Image,1025, 850, 200, 100);

    }

    public void render(SpriteBatch batch){

    }
}
