package com.dorifto.stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.dorifto.GameWorld;

import com.dorifto.entity.Submarine;

public class SubmarinePre extends LevelStage {

	private Music screenMusic;
	private Sound selectionConfirmed;
	private Texture background;
	
	private Submarine submarine;
	
	private Controller mainController;
	private ControllerAdapter mainAdapter;

	public void buildStage() {
		isWon = false;
		mainAdapter = new ControllerAdapter() {
			public boolean buttonDown(Controller controller, int buttonCode) {
				if (buttonCode == controller.getMapping().buttonA) {
					screenMusic.stop();
					selectionConfirmed.play();
					isWon = true;
				}
				return false;
			}
		};
		mainController = GameWorld.getControllers().get(0);
		screenMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/title_screen_music.mp3"));
		background = new Texture("Backgrounds/menu_background.png");
		submarine = new Submarine();
		submarine.getSprite().setScale(0.35f);
		submarine.getSprite().setCenterX(screen.width + submarine.getWidth() * submarine.getScaleX() / 2);
		submarine.getSprite().setCenterY(screen.height/2);
		
		selectionConfirmed = Gdx.audio.newSound(Gdx.files.internal("sounds/selection_confirm.wav"));
		
		mainController.addListener(mainAdapter);
	}
	
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.updateSubmarine();
		
		batch.begin();
		batch.draw(background, screen.x, screen.y, screen.width, screen.height);
		submarine.draw(batch, 0.0f);
		batch.end();
		
		batch.begin();
		font.getData().setScale(3);
		font.draw(batch, "Votre sous marin est attaqué !" ,
				Gdx.graphics.getWidth()/3.0f, Gdx.graphics.getHeight()/2.0f);
		font.getData().setScale(2.0f);
		font.draw(batch, "Rebouchez les trous du sous-marin en déplacant \n le curseur avec Stick Gauche et en appuyant sur A.", Gdx.graphics.getWidth()/3.0f, Gdx.graphics.getHeight()/2.5f);
		batch.end();
		
		
		
		
		batch.begin();
		font.draw(batch, "Appuyez sur A pour commencer", Gdx.graphics.getWidth()/3.0f, Gdx.graphics.getHeight()/3.0f);
		font.getData().setScale(1.5f);
		batch.end();
	}
	
	public void updateSubmarine() {
		float x = submarine.getSprite().getX() - 10;
		if (x < (submarine.getSprite().getWidth() - submarine.getSprite().getWidth() * submarine.getSprite().getScaleX()) * -1)
			x = screen.width / 2 + submarine.getSprite().getWidth() * submarine.getSprite().getScaleX() / 2;
		this.submarine.getSprite().setPosition(x, submarine.getY());
	}
	
	public void dispose() {
		mainController.removeListener(mainAdapter);
	}
}
