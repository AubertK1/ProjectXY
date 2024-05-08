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
    public static String upP1Text = "W";
    public static TextButton upP1;
    public static String downP1Text = "S";
    public static TextButton downP1;
    public static String rightP1Text = "A";
    public static TextButton rightP1;
    public static String leftP1Text = "D";
    public static TextButton leftP1;
    public static String lightP1Text = "F";
    public static TextButton lightP1;
    public static String heavyP1Text = "G";
    public static TextButton heavyP1;
    public static String interactP1text = "E";
    public static TextButton interactP1;
    public static String upP2Text = Input.Keys.toString(Input.Keys.UP);
    public static TextButton upP2;
    public static String downP2Text = Input.Keys.toString(Input.Keys.DOWN);
    public static TextButton downP2;

    public static String rightP2Text = Input.Keys.toString(Input.Keys.RIGHT);
    public static TextButton rightP2;
    public static  String leftP2Text = Input.Keys.toString(Input.Keys.LEFT);
    public static TextButton leftP2;
    public static String lightP2Text = Input.Keys.toString(Input.Keys.PERIOD);
    public static TextButton lightP2;
    public static String heavyP2Text = Input.Keys.toString(Input.Keys.SLASH);
    public static TextButton heavyP2;

    public static String interactP2text = Input.Keys.toString(Input.Keys.CONTROL_RIGHT);
    public static TextButton interactP2;

    /*
     * This is where you can see the keybinds that control the character and the moves
     */
    public ControlScreen() {
/*
moves and abilities
 */
        jumpLabel = createLabel("JUMP", 450, 750, 150, 100);
        downLabel = createLabel("DOWN", 450, 650, 150, 100);
        rightLabel = createLabel("RIGHT", 450, 550, 150, 100);
        leftLabel = createLabel("LEFT", 450, 450, 150, 100);
        interactLabel = createLabel("INTERACT", 450, 350, 100, 75);
        lightLabel = createLabel("HEAVY", 450, 250, 150, 100);
        heavyLabel = createLabel("LIGHT", 450, 150, 150, 100);


/*
Back button to the menu screen and a button to reset the keyninds to their original values
 */
        final TextButton play = createTextButton("BACK",25,550,200,200);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Main.changeScreen("MainMenu");
            }
        });
        final TextButton defaultKeys = createTextButton("DEFAULT KEYBINDS",25,300,200,200);
            defaultKeys.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    KeyBinds.resetKeyBinds();
                }
            });

            /*
            The buttons
             */
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
        interactP1 = createTextButton("E", 550, 350, 100, 75);
        interactP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(interactP1text);
            }
        });
        lightP1 = createTextButton("F", 550, 250, 100, 75);
        lightP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(lightP1Text);
            }
        });
        heavyP1 = createTextButton("G", 550, 150, 100, 75);
        heavyP1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(heavyP1Text);
            }
        });



        upP2 = createTextButton(upP2Text, 1050, 750, 100, 75);
        upP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(upP2Text);
            }
        });
        downP2 = createTextButton(downP2Text, 1050, 650, 100, 75);
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

        interactP2 = createTextButton("CTRL R", 1050, 350, 100, 75);
        interactP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(interactP2text);
            }
        });
        lightP2 = createTextButton("PERIOD", 1050, 250, 100, 75);
        lightP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(lightP2Text);
            }
        });
        heavyP2 = createTextButton("SLASH", 1050, 150, 100, 75);
        heavyP2.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                oldKey = Input.Keys.valueOf(heavyP2Text);
            }
        });




    }

    public void render(SpriteBatch batch) {
        //batch.draw(background,0, 0,2000,1000);

        batch.draw(player1Image, 525, 850, 200, 100);
        batch.draw(player2Image, 1025, 850, 200, 100);

    }

    /**
     * Changes the button text
     * @param newKey The new keybinds
     */
    public void changeButtonText(int newKey) {

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
        } else if (Input.Keys.toString(oldKey).equalsIgnoreCase(interactP2text)) {
            interactP2text = Input.Keys.toString(newKey);
            interactP2.setText(interactP2text);
        }
    }
}


