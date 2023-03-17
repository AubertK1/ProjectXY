package com.mygdx.game.OI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;

public class MenuScreen extends Screen {



    public MenuScreen() {
        UI.setPosition(0,0);
    }


    public TextButton createButton(String buttonText,int x, int y, int width, int height){
        final TextButton aTextbutton = new TextButton(buttonText,skin);
        aTextbutton.setPosition(x,y);
        aTextbutton.setSize(width,height);
        UI.addActor(aTextbutton);
        return aTextbutton;
    }
}
