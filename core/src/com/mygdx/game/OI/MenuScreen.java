package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Main;

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
    public ImageButton createImageButton(String filePath,int x, int y, int width, int height){
        Texture playTexture = new Texture(filePath);
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(playTexture));
        final ImageButton imageButton = new ImageButton(drawable);
        imageButton.setPosition(x,y);
        imageButton.setSize(width,height);
        UI.addActor(imageButton);
        return imageButton;
    }
}
