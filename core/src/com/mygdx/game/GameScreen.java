package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen {
    Player player1;
    Player player2;
    Platform stage;
    Texture background;
    public GameScreen(){
        background = new Texture("assets\\textures\\stockbg.jpg");
    }
    public void render(SpriteBatch batch){
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
}
