package com.dorifto.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dorifto.GameWorld;

public abstract class AbstractScreen extends Stage implements Screen {
	
	protected OrthographicCamera camera;
	
	protected SpriteBatch batch;
	protected BitmapFont font;
	protected Rectangle screen;
	
	public AbstractScreen() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // INITIALISE LA CAMERA A LA TAILLE
		
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		batch = new SpriteBatch();
		
		screen = new Rectangle();
		screen.x = 0;
		screen.y = 0;
		screen.width = Gdx.graphics.getWidth();
		screen.height = Gdx.graphics.getHeight();
	}
	
	public abstract void buildStage();

	@Override
	public void render(float delta) {
		// EMERGENCY EXIT
		EMERGENCY_EXIT();
			
		// Clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		// Calling to Stage methods
		super.act(delta);
		super.draw();
	}
	
	public void EMERGENCY_EXIT() {
		if(GameWorld.getControllers().get(0).getButton(GameWorld.getControllers().get(0).getMapping().buttonStart))
			Gdx.app.exit();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
}
