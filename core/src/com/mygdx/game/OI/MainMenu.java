package com.mygdx.game.OI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;

public class MainMenu {
    Skin skin = Main.skin;
    Stage stage = Main.stage;
    // Makes a new group
    Group myGroup = new Group();
    public MainMenu() {
        myGroup.setPosition(0,0);

        String myString = "PLAY";
        final TextButton myTextbutton = new TextButton(myString,skin);
        myTextbutton.setPosition(350,550);
        myTextbutton.setSize(200,200);
        myTextbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myTextbutton.setText("click");
                Main.isMatchRunning = true;
            }
        });

        myGroup.addActor(myTextbutton);
    }
    public void startDrawing(){
        stage.addActor(myGroup);
    }
    public void stopDrawing(){
        myGroup.remove();
    }
}
