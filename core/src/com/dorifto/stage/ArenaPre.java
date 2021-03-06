package com.dorifto.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.dorifto.GameWorld;

public class ArenaPre extends LevelStage {
	private Music screenMusic;
	private Sound selectionConfirmed;
	private Texture background;
	private Controller mainController;
	private ControllerAdapter mainAdapter;
	private Sprite poseidon;
	private Sprite dialog;
	private long buildTime;

	@Override
	public void buildStage() {
		dialog = new Sprite(new Texture(Gdx.files.internal("Arena/boite_dialogue.png")));
		dialog.flip(false, true);
		dialog.setScale(1.0f/3.0f);
		poseidon = new Sprite(new Texture(Gdx.files.internal("Arena/poseidon.png")));
		buildTime = System.currentTimeMillis();
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
		background = new Texture("Backgrounds/menu_background.png");//TODO
		selectionConfirmed = Gdx.audio.newSound(Gdx.files.internal("sounds/selection_confirm.wav"));
		
		mainController.addListener(mainAdapter);
	}
	
	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(background, screen.x, screen.y, screen.width, screen.height);
		batch.end();
		
		batch.begin();
		poseidon.draw(batch,1.0f);
		poseidon.setScale(1.0f/3.0f);
		poseidon.setCenterX(2.3f*Gdx.graphics.getWidth()/3.0f);
		poseidon.setCenterY(Gdx.graphics.getHeight()-800.0f);
		batch.end();
		
		batch.begin();
		dialog.draw(batch);
		dialog.setCenterX(Gdx.graphics.getWidth()/2.0f -50.0f);
		dialog.setCenterY(Gdx.graphics.getHeight()/2.0f -20.0f);
		batch.end();
		
		
		
		batch.begin();
		
		font.setColor(Color.BLACK);
		font.draw(batch, "Seul UN pourra retourner sur la terre ferme !", Gdx.graphics.getWidth()/3.0f, Gdx.graphics.getHeight()/2.0f);
		font.getData().setScale(2.0f);
		batch.end();
		
		batch.begin();
		font.setColor(Color.WHITE);
		font.draw(batch, "D??placez-vous avec Stick Gauche, attaquez avec A",Gdx.graphics.getWidth()/3.0f, Gdx.graphics.getHeight()/2.0f - 20.0f*font.getLineHeight() );
		batch.end();
		
		batch.begin();
		font.draw(batch, "Appuyez sur A pour Continuer", Gdx.graphics.getWidth()/3.0f, Gdx.graphics.getHeight()/3.0f);
		font.getData().setScale(1.5f);
		batch.end();
	}
	
	public void dispose() {
		mainController.removeListener(mainAdapter);
	}

}
