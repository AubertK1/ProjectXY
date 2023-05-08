package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.OI.ControlScreen;
import com.mygdx.game.OI.MainMenu;
import com.mygdx.game.OI.P1CharacterSelectScreen;
import com.mygdx.game.OI.P2CharacterSelectScreen;
import com.mygdx.game.OI.Screen;

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
	static MainMenu mainMenu;
	static ControlScreen controlScreen;
	//game screen
	 public static GameScreen gameScreen;
	public static P1CharacterSelectScreen characterSelectScreen;
	public static P2CharacterSelectScreen characterSelectScreen2;
	public static Screen currentScreen;
	//detects whether the game is currently being played or not
	public static String activeScreenName = "MainMenu";
	//shows hitboxes
	public static boolean inDebugMode = true;

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
		mainMenu = new MainMenu();
		controlScreen = new ControlScreen();
		//setting up the game screen
		gameScreen = new GameScreen();
		characterSelectScreen = new P1CharacterSelectScreen();
		characterSelectScreen2 = new P2CharacterSelectScreen();
		currentScreen = mainMenu;//Set starting screen
		currentScreen.startDrawing();

		//this is so the user can click on the screen
		InputMultiplexer multiplexer = new InputMultiplexer();
		//This handles any interactions with the screen/keyboard
		InputProcessor screenProcessor = new InputProcessor() {
			@Override
			public boolean keyDown(int keycode) {
				//if control screen
				if(currentScreen == controlScreen){
					if (controlScreen.oldKey  != -1){
						boolean changed = KeyBinds.changeKeyBind(controlScreen.oldKey,keycode);
						if(changed) {
							controlScreen.changeButtonText(keycode);
							controlScreen.oldKey  = -1;
						}
					}
					return true;
				}
				else {
					int affectedPlayer = getAffectedPlayer(keycode);
					if (affectedPlayer != -1)
						GameScreen.getPlayers().get(getAffectedPlayer(keycode)).interact(keycode);
					return true;
				}
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
		ScreenUtils.clear(new Color(0x961d1dff));
		batch.begin();

		currentScreen.render(batch);

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

	static public void changeScreen(String newScreenName){
		currentScreen.stopDrawing();
		if(newScreenName.equals("MainMenu")){
				currentScreen= mainMenu;
		}else if(newScreenName.equals("GameScreen")){
			currentScreen=gameScreen;
		}else if(newScreenName.equals("ControlScreen")){
			currentScreen=controlScreen;
		} else if (newScreenName.equals("P1CharacterSelectScreen")){
			currentScreen=characterSelectScreen;
		} else if (newScreenName.equals("P2CharacterSelectScreen")){
			currentScreen=characterSelectScreen2;
		}
		currentScreen.startDrawing();
	}

	public static float getFrameRate(){
		return Gdx.graphics.getDeltaTime();
	}

	public int getAffectedPlayer(int KEY){
		return KeyBinds.findKeySetIndex(KEY);
	}


}
