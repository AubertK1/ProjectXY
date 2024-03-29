package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Main;


public class Screen {
    protected Texture background;
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
        batch.draw(background,0, 0,2000,1000);
    }
}
