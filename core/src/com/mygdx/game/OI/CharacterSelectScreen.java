package com.mygdx.game.OI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;

public class CharacterSelectScreen extends MenuScreen{


    public Texture characterSelectImage = new Texture("textures/CHOOSE_YOUR_CHARACTER.png");

    public CharacterSelectScreen() {
        final ImageButton fight = createImageButton("textures/FIGHT.PNG", 25, 550, 200, 200);
        fight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Main.changeScreen("GameScreen");
            }
        });
    }


    public void render(SpriteBatch batch){
       // batch.draw(background,0, 0,2000,1000);
        batch.draw(characterSelectImage,500, -10, 400, 200);
    }

    }

