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
	//Houses all the actors so that they can be setup on the screen together and drawn together
	static Group group;
	//a spritesheet of all the actors (an actor is a button, textfield, label, etc.)
	static Skin skin;

	/**
	 * This class sets up the screen. It's only called ONCE (when the game is loaded)
	 */
	@Override
	public void create () {
		//initializing everything
		batch = new SpriteBatch();
		stage = new Stage();
		group = new Group();
		skin = new Skin(Gdx.files.internal("assets\\skin\\uiskin.json"));

		group.setPosition(Gdx.graphics.getWidth()/2f, 200);
		Label tempLabel = new Label("This is an actor", skin);
		//position is relative to the group position
		tempLabel.setPosition(0, 200);
		TextField tempTF = new TextField("So is this...", skin);
		tempTF.setPosition(0, tempLabel.getY()-tempTF.getHeight());
		final TextButton tempButton2 = new TextButton("so is the button below this (CLICK IT)",skin);
		tempButton2.setPosition(0, tempTF.getY()-tempButton2.getHeight());
		final Button tempButton = new Button(skin);
		tempButton.addListener(new ClickListener(){
			public void clicked (InputEvent event, float x, float y) {
				tempButton2.setText("You clicked the button below me !!");
			}
		});
		tempButton.setSize(150, 30);
		tempButton.setPosition(0, tempButton2.getY()-tempButton.getHeight());
		final SelectBox<String> tempSB = new SelectBox(skin);
		tempSB.setSize(300, 30);
		tempSB.setPosition(0, tempButton.getY()-tempSB.getHeight());
		tempSB.setItems("Click Here","Anyway you get the point", "these are gonna be used in", "the main menu screen mostly", "but they're useful to know for this");

		group.addActor(tempLabel);
		group.addActor(tempTF);
		group.addActor(tempButton2);
		group.addActor(tempButton);
		group.addActor(tempSB);

		stage.addActor(group);
		//so the user can click on the actors
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
		batch.end();
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
