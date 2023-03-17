package com.mygdx.game.OI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Main;
import com.mygdx.game.Platform;
import com.mygdx.game.Weapons.Weapon;

public class Screen {
    protected Skin skin = Main.skin;
    protected Stage stage = Main.stage;
    // Makes a new group
   protected Group UI = new Group();
    public void startDrawing(){
        stage.addActor(UI);
    }
    public void stopDrawing(){
        UI.remove();
    }
    public void render(SpriteBatch batch){

    }
}
