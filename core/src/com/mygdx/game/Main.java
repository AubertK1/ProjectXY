package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.OI.MainMenu;

/**
 * This is the real Main class that all of your code will go into
 */
public class Main extends ApplicationAdapter {
	static SpriteBatch batch;
	//Houses all the groups so that they can all be drawn together
	public static Stage stage;
	//a spritesheet of all the actors (an actor is a button, textfield, label, etc.)
	public static Skin skin;
	//main menu screen
	MainMenu menu;
	//game screen
	public static GameScreen gameScreen;
	//detects whether the game is currently being played or not
	public static boolean isMatchRunning = false;

	/**
	 * This class sets up the screen. It's only called ONCE (when the game is loaded)
	 */
	@Override
	public void create () {
		//initializing the batch
		batch = new SpriteBatch();
		//initializing the stage
		stage = new Stage();
		//initializing the skin
		skin = new Skin(Gdx.files.internal("assets\\skin\\uiskin.json"));

		//setting up main menu screen
		menu = new MainMenu();
		//setting up the game screen
		gameScreen = new GameScreen();

		//this is so the user can click on the screen
		InputMultiplexer multiplexer = new InputMultiplexer();
		//This handles any interactions with the screen/keyboard
		InputProcessor screenProcessor = new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				gameScreen.getPlayers().get(getAffectedPlayer(keycode)).interact(keycode);
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				return false;
			}

			@Override
			public boolean keyTyped(char character) {
				return false;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				return false;
			}

			@Override
			public boolean scrolled(float amountX, float amountY) {
				return false;
			}
		};
		multiplexer.addProcessor(stage);
		multiplexer.addProcessor(screenProcessor);
		Gdx.input.setInputProcessor(multiplexer);
	}

	/**
	 * This class renders everything to the screen. It's called EVERY FRAME, so it can re-render the screen
	 */
	@Override
	public void render () {
		//sets the background color. Later we'll set a background image over this
		ScreenUtils.clear(new Color(0x3fc9e8ff));
		batch.begin();

		//if match is not being played, show main menu screen
		if (isMatchRunning == false){
			menu.startDrawing();
		}
		else{ //if match is being played show gamescreen
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
	public static float getFrameRate(){
		return Gdx.graphics.getDeltaTime();
	}
	public int getAffectedPlayer(int KEY){
		if(KEY == 19 || KEY == 20 || KEY == 21 || KEY == 22) return 1;
		else return 0;
	}
}
