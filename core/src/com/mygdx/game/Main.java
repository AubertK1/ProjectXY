package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * This is the real Main class that all of your code will go into
 */
public class Main extends ApplicationAdapter {
	static SpriteBatch batch;
	//Houses all the groups so that they can all be drawn together
	static Stage stage;
	//a spritesheet of all the actors (an actor is a button, textfield, label, etc.)
	static Skin skin;
	//main menu screen
	MainMenu menu;
	//game screen
	GameScreen gameScreen;
	//detects whether the game is currently being played or not
	static boolean isGameRunning = false;

	/**
	 * This class sets up the screen. It's only called ONCE (when the game is loaded)
	 */
	@Override
	public void create () {
		//initializing everything
		batch = new SpriteBatch();
		stage = new Stage();
		skin = new Skin(Gdx.files.internal("assets\\skin\\uiskin.json"));
		//setting main menu screen
		menu = new MainMenu();
		gameScreen = new GameScreen();

		//this is so the user can click on the screen
		Gdx.input.setInputProcessor(stage);
	}

	/**
	 * This class renders everything to the screen. It's called EVERY FRAME, so it can re-render the screen
	 */
	@Override
	public void render () {
		//sets the background color. Later we'll set a background image over this
		ScreenUtils.clear(new Color(0x3fc9e8ff));
		batch.begin();

		//if game is not being played, show main menu screen
		if (isGameRunning == false){
			menu.draw();
		}
		else{
			menu.stopDrawing();
			gameScreen.render(batch);
		}

		batch.end();
		//draws the actors on the screen
		stage.draw();

	}

	/**
	 * This class safely disposes any unnecessary objects. It's called ONCE as well (When the application closes)
	 */
	@Override
	public void dispose () {
		batch.dispose();
	}
}
