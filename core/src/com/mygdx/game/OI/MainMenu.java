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
        myTextbutton.setPosition(25,550);
        myTextbutton.setSize(200,200);
        myTextbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                myTextbutton.setText("click");
                Main.isMatchRunning = true;
            }
        });
        String aString = "SELECT CHARACTER";
        final TextButton aTextbutton = new TextButton(aString,skin);
        aTextbutton.setPosition(25,300);
        aTextbutton.setSize(200,200);
        aTextbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                aTextbutton.setText("click");
                Main.isMatchRunning = true;
            }
        });

        String bString = "Controls";
        final TextButton bTextbutton = new TextButton(myString,skin);
        myTextbutton.setPosition(25,100);
        myTextbutton.setSize(200,200);
        bTextbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                bTextbutton.setText("click");
                Main.isMatchRunning = true;
            }
        });
        myGroup.addActor(myTextbutton);
        myGroup.addActor(aTextbutton);

    }
    public void startDrawing(){
        stage.addActor(myGroup);
    }
    public void stopDrawing(){
        myGroup.remove();
    }


}
