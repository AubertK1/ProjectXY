package com.mygdx.game.OI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.KeyBinds;
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

    public int oldKey = -1;
    String upP1Text = "W";
    TextButton upP1;
    String downP1Text = "S";
    TextButton downP1;
    String rightP1Text = "A";
    TextButton rightP1;
    String leftP1Text = "D";
    TextButton leftP1;
    String lightP1Text = "F";
    TextButton lightP1;
    String heavyP1Text = "G";
    TextButton heavyP1;
    String tempP1Text = "Y";
    TextButton tempP1;
    String interactP1text = "E";
    TextButton interactP1;
    String upP2Text = "";
    TextButton upP2;
    String downP2Text = "";
    TextButton downP2;

    String rightP2Text = "";
    TextButton rightP2;
    String leftP2Text = "";
    TextButton leftP2;
    String upP2Text = "";
    TextButton upP2;



    public ControlScreen() {

        jumpLabel = createLabel("Jump",450,750,150,100);
        downLabel = createLabel("Down",450,650,150,100);
        rightLabel = createLabel("Right",450,550,150,100);
        leftLabel  = createLabel("Left",450,450,150,100);
        lightLabel = createLabel("Light",450,350,150,100);
        heavyLabel = createLabel("Heavy",450,250,150,100);
        tempLabel = createLabel("Temp",450,  150,150,100);
        interactLabel = createLabel("Interact",450,50,100,75);

        final TextButton play = createTextButton("BACK", 25, 550, 200, 200);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.changeScreen("MainMenu");
            }
        });

              upP1 =  createTextButton(upP1Text, 550, 750,100,75);
            upP1.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    oldKey = Input.Keys.valueOf(upP1Text);
                }
            });
             downP1 = createTextButton(downP1Text,550, 650,100,75);
            downP1.addListener(new ChangeListener() {
                @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        oldKey = Input.Keys.valueOf(downP1Text);
                }
            });
             rightP1 =  createTextButton("D",550, 550,100,75);
        rightP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(rightP1Text);
            }
        });

            leftP1 = createTextButton("A",550, 450,100,75);
        leftP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(leftP1Text);
            }
        });

             lightP1 =  createTextButton("F",550, 350,100,75);
        lightP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(lightP1Text);
            }
        });
             heavyP1 =  createTextButton("G",550, 250,100,75);
        heavyP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(heavyP1Text);
            }
        });


            tempP1 =   createTextButton("Y",550, 150,100,75);
        tempP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(tempP1Text);
            }
        });
            interactP1 =  createTextButton("E",550, 50,100,75);
        interactP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(interactP1text);
            }
        });


             upP2 = createTextButton("UP ARROW",1050, 750,100,75);
        upP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(upP1Text);
            }
        });
             downP2 = createTextButton("DOWN ARROW",1050, 650,100,75);
        downP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(downP2Text);
            }
        });
            rightP2 = createTextButton("RIGHT ARROW",1050, 550,100,75);
        rightP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(rightP2Text);
            }
        });
            final TextButton leftP2 = createTextButton("LEFT ARROW",1050, 450,100,75);
        rightP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(rightP1Text);
            }
        });
            final TextButton lightP2 =createTextButton("COMMA",1050, 350,100,75);
        rightP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(rightP1Text);
            }
        });
            final TextButton heavyP2 =createTextButton("PERIOD",1050, 250,100,75);
        rightP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(rightP1Text);
            }
        });
            final TextButton tempP2 = createTextButton("/",1050, 150,100,75);
        rightP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(rightP1Text);
            }
        });
            final TextButton interactP2 = createTextButton("CTRL R",1050, 50,100,75);
        rightP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(rightP1Text);
            }
        });

        }

    public void render(SpriteBatch batch){
        //batch.draw(background,0, 0,2000,1000);

        batch.draw(player1Image,525,850, 200,100);
        batch.draw(player2Image,1025, 850, 200, 100);

    }

    public void changeButtonText(int newKey){
        //fixme find correct button based on old key
        if(Input.Keys.toString(oldKey).equalsIgnoreCase(upP1Text)){
            upP1Text = Input.Keys.toString(newKey);
            upP1.setText(upP1Text);
        }
        else{

        }
    }

}
