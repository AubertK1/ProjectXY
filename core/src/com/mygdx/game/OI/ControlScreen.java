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
    String upP2Text = Input.Keys.toString(Input.Keys.UP);
    TextButton upP2;
    String downP2Text = Input.Keys.toString(Input.Keys.DOWN);
    TextButton downP2;

    String rightP2Text = Input.Keys.toString(Input.Keys.RIGHT);
    TextButton rightP2;
    String leftP2Text = Input.Keys.toString(Input.Keys.LEFT);
    TextButton leftP2;
    String lightP2Text = Input.Keys.toString(Input.Keys.CONTROL_RIGHT);
    TextButton lightP2;
    String heavyP2Text = Input.Keys.toString(Input.Keys.COMMA);
    TextButton heavyP2;
    String tempP2Text = Input.Keys.toString(Input.Keys.BACKSLASH);
    TextButton tempP2;
    String interactP2text = Input.Keys.toString(Input.Keys.PERIOD);
    TextButton interactP2;


    public ControlScreen() {

        jumpLabel = createLabel("JUMP", 450, 750, 150, 100);
        downLabel = createLabel("DOWN", 450, 650, 150, 100);
        rightLabel = createLabel("RIGHT", 450, 550, 150, 100);
        leftLabel = createLabel("LEFT", 450, 450, 150, 100);
        lightLabel = createLabel("LIGHT", 450, 350, 150, 100);
        heavyLabel = createLabel("HEAVY", 450, 250, 150, 100);
        tempLabel = createLabel("TEMP", 450, 150, 150, 100);
        interactLabel = createLabel("INTERACT", 450, 50, 100, 75);

        final TextButton play = createTextButton("BACK", 25, 650, 200, 200);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.changeScreen("MainMenu");
            }
        });
        final TextButton defaultKeys = createTextButton("DEFAULT KEYBINDS",25,350,200,200);
            defaultKeys.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    KeyBinds.resetKeyBinds();
                }
            });

        upP1 = createTextButton(upP1Text, 550, 750, 100, 75);
        upP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(upP1Text);
            }
        });
        downP1 = createTextButton(downP1Text, 550, 650, 100, 75);
        downP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(downP1Text);
            }
        });
        rightP1 = createTextButton("D", 550, 550, 100, 75);
        rightP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(rightP1Text);
            }
        });

        leftP1 = createTextButton("A", 550, 450, 100, 75);
        leftP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(leftP1Text);
            }
        });

        lightP1 = createTextButton("F", 550, 350, 100, 75);
        lightP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(lightP1Text);
            }
        });
        heavyP1 = createTextButton("G", 550, 250, 100, 75);
        heavyP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(heavyP1Text);
            }
        });


        tempP1 = createTextButton("Y", 550, 150, 100, 75);
        tempP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(tempP1Text);
            }
        });
        interactP1 = createTextButton("E", 550, 50, 100, 75);
        interactP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(interactP1text);
            }
        });


        upP2 = createTextButton("UP ARROW", 1050, 750, 100, 75);
        upP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(upP2Text);
            }
        });
        downP2 = createTextButton("DOWN ARROW", 1050, 650, 100, 75);
        downP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(downP2Text);
            }
        });
        rightP2 = createTextButton("RIGHT ARROW", 1050, 550, 100, 75);
        rightP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(rightP2Text);
            }
        });
        leftP2 = createTextButton("LEFT ARROW", 1050, 450, 100, 75);
        leftP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(leftP2Text);
            }
        });
        lightP2 = createTextButton("COMMA", 1050, 350, 100, 75);
        lightP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(lightP2Text);
            }
        });
        heavyP2 = createTextButton("PERIOD", 1050, 250, 100, 75);
        heavyP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(heavyP2Text);
            }
        });
        tempP2 = createTextButton(Input.Keys.toString(Input.Keys.BACKSLASH), 1050, 150, 100, 75);
        tempP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(tempP2Text);
            }
        });
        interactP2 = createTextButton("CTRL R", 1050, 50, 100, 75);
        interactP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(interactP2text);
            }
        });

    }

    public void render(SpriteBatch batch) {
        //batch.draw(background,0, 0,2000,1000);

        batch.draw(player1Image, 525, 850, 200, 100);
        batch.draw(player2Image, 1025, 850, 200, 100);

    }

    public void changeButtonText(int newKey) {
        //fixme find correct button based on old key
        if (Input.Keys.toString(oldKey).equalsIgnoreCase(upP1Text)) {
            upP1Text = Input.Keys.toString(newKey);
            upP1.setText(upP1Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(downP1Text)) {
            downP1Text = Input.Keys.toString(newKey);
            downP1.setText(downP1Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(rightP1Text)) {
            rightP1Text = Input.Keys.toString(newKey);
            rightP1.setText(rightP1Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(leftP1Text)) {
            leftP1Text = Input.Keys.toString(newKey);
            leftP1.setText(leftP1Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(lightP1Text)) {
            lightP1Text = Input.Keys.toString(newKey);
            lightP1.setText(lightP1Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(heavyP1Text)) {
            heavyP1Text = Input.Keys.toString(newKey);
            heavyP1.setText(heavyP1Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(tempP1Text)) {
            tempP1Text = Input.Keys.toString(newKey);
            tempP1.setText(tempP1Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(interactP1text)) {
            interactP1text = Input.Keys.toString(newKey);
            interactP1.setText(interactP1text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(upP2Text)) {
            upP2Text = Input.Keys.toString(newKey);
            upP2.setText(upP2Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(downP2Text)) {
            downP2Text = Input.Keys.toString(newKey);
            downP2.setText(downP2Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(rightP2Text)) {
            rightP2Text = Input.Keys.toString(newKey);
            rightP2.setText(rightP2Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(leftP2Text)) {
            leftP2Text = Input.Keys.toString(newKey);
            leftP2.setText(leftP2Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(lightP2Text)) {
            lightP2Text = Input.Keys.toString(newKey);
            lightP2.setText(lightP2Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(heavyP2Text)) {
            heavyP2Text = Input.Keys.toString(newKey);
            heavyP2.setText(heavyP2Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(tempP2Text)) {
            tempP2Text = Input.Keys.toString(newKey);
            tempP2.setText(tempP2Text);
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(interactP2text)) {
            interactP2text = Input.Keys.toString(newKey);
            interactP2.setText(interactP2text);
        }
    }
}


