package com.mygdx.game.OI;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;

public class CharacterSelectScreen extends MenuScreen{

    @Override
    public Image characterSelect(filePath, x, y, width, height);

    final ImageButton play = createImageButton("textures/playTemp.PNG",25,550,200,200);
        play.addListener(new ChangeListener() {
        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            Main.changeScreen("GameScreen");
        }
    });




    }
}
