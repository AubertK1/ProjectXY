package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen extends Screen {



    public MenuScreen() {
        UI.setPosition(0,0);
    }


    public TextButton createTextButton(String buttonText,int x, int y, int width, int height){
        final TextButton aTextbutton = new TextButton(buttonText,skin);
        aTextbutton.setPosition(x,y);
        aTextbutton.setSize(width,height);
        UI.addActor(aTextbutton);
        return aTextbutton;
    }
    public Button createImageButton(String filePath,int x, int y, int width, int height){
        Texture playTexture = new Texture(filePath);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        final Button imageButton = new Button(drawable);
        imageButton.setPosition(x,y);
        imageButton.setSize(width,height);
        UI.addActor(imageButton);
        return imageButton;
    }

    public Label createLabel(String buttonText, int x, int y, int width, int height){
        final Label aLabel = new Label(buttonText,skin);
        aLabel.setPosition(x,y);
        aLabel.setSize(width,height);
        UI.addActor(aLabel);
        return aLabel;
    }
}
