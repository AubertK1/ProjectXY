package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenu {
    Skin skin = Main.skin;
    Stage stage = Main.stage;
    // Makes a new group
    Group myGroup = new Group();
    public MainMenu() {
        myGroup.setPosition(0,0);

        String myString = "Play";
        final TextButton myTextbutton = new TextButton(myString,skin);
        myTextbutton.setPosition(800,600);
        myTextbutton.setSize(400,400);
        myTextbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myTextbutton.setText("click");
                Main.isGameRunning = true;
            }
        });

        myGroup.addActor(myTextbutton);
    }
    public void draw(){
        stage.addActor(myGroup);
    }
    public void stopDrawing(){
        myGroup.remove();
    }
}
