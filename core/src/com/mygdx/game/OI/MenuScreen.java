package com.mygdx.game.OI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;

public class MenuScreen {
    Skin skin = Main.skin;
    Stage stage = Main.stage;
    // Makes a new group
    Group myGroup = new Group();

    public MenuScreen() {
        myGroup.setPosition(0,0);
    }

    public void startDrawing(){
        stage.addActor(myGroup);
    }
    public void stopDrawing(){
        myGroup.remove();
    }

    public TextButton createButton(String buttonText,int x, int y, int width, int height){
        final TextButton aTextbutton = new TextButton(buttonText,skin);
        aTextbutton.setPosition(x,y);
        aTextbutton.setSize(width,height);
        myGroup.addActor(aTextbutton);
        return aTextbutton;
    }
}
