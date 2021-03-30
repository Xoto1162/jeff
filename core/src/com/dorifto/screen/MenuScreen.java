package com.dorifto.screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import com.dorifto.GameWorld;
import com.dorifto.entity.Bulle;
import com.dorifto.entity.player.Player;

public class MenuScreen extends AbstractScreen {
	
	private Music titleScreenMusic;
	private Texture titleScreen;
	private Texture selectorTexture;
	private Rectangle screen;
	private Rectangle selector;
	private Texture Jeff;
	private Sprite JeffPoulpe;
	
	private Sound nani;
	
	private int playerCount = 1;
	
	private Sound selectionYes;
	private Sound selectionNo;
	private Sound selectionConfirmed;
	private Sound selectionMove;
	private int selectorPosition;
	private int selectorPossibilities = 2;
	private float selectorYPositions[] = {800, 700};
	
	private long lastAction;
	
	private ArrayList<Bulle> bulles;
	private long lastBubble;
	
	private Controller mainController;
	private ControllerAdapter inputController;
	
	@Override
	public void buildStage() {
		// Title screen ressources
	    titleScreenMusic = Gdx.audio.newMusic(Gdx.files.internal("menu_music.mp3"));
	    titleScreenMusic.setLooping(true);
	    titleScreenMusic.setVolume(0.4f);
	    titleScreenMusic.play();
		titleScreen = new Texture(Gdx.files.internal("Backgrounds/main_menu.png"));
		screen = new Rectangle();
		screen.x = 0;
		screen.y = 0;
		screen.width = Gdx.graphics.getWidth();
		screen.height = Gdx.graphics.getHeight();
		bulles = new ArrayList<Bulle>();
		Jeff = new Texture(Gdx.files.internal("jeff.png"));
		JeffPoulpe = new Sprite(Jeff);
		JeffPoulpe.setCenterX(600);
		JeffPoulpe.setCenterY(600);
		JeffPoulpe.rotate(15f);
		JeffPoulpe.setScale(0.8f);
		
		// Selector
		selectorTexture = new Texture(Gdx.files.internal("selector.png"));
		selector = new Rectangle();
		selector.x = 1300;
		selector.y = 400;
		selector.width = 64;
		selector.height = 64;
		selectorPosition = 0;
		
		// Audio ressources
		selectionYes = Gdx.audio.newSound(Gdx.files.internal("sounds/selection_yes.wav"));
		selectionNo = Gdx.audio.newSound(Gdx.files.internal("sounds/selection_no.wav"));
		selectionConfirmed = Gdx.audio.newSound(Gdx.files.internal("sounds/selection_confirm.wav"));
		selectionMove = Gdx.audio.newSound(Gdx.files.internal("sounds/selection_move.wav"));
		

		// Assigning first controller as the main controller
		mainController = GameWorld.getControllers().get(0);
		inputController = new ControllerAdapter() {
			public boolean buttonDown(Controller controller, int buttonCode) {
				if (buttonCode == controller.getMapping().buttonA) {
					if(selectorPosition == 0) {
						titleScreenMusic.stop();
						selectionConfirmed.play();
						GameWorld.setPlayerCount(playerCount);
						ScreenManager.getInstance().showScreen(new GameScreen(GameWorld.getStages()));
					}
					if(selectorPosition == 1)
						Gdx.app.exit();
				}
				if(controller.getMapping().buttonL1 == buttonCode) {
					if(playerCount > 2) {
						playerCount--;
						selectionYes.play();
						removePlayer();
					}
					else
						selectionNo.play();
				}
				if(controller.getMapping().buttonR1 == buttonCode) {
					if(playerCount < 4) {
						playerCount++;
						selectionYes.play();
						addPlayer();
					}
					else
						selectionNo.play();
				}
				
				return false;
			}
		};
		
		for(int i = 0; i < playerCount; i++) {
			addPlayer();
		}
		
		mainController.addListener(inputController);
		spawnBubule();
		
		//nani = Gdx.audio.newSound(Gdx.files.internal("nani.mp3"));
		//nani.play();
	}
	
	public void render(float delta) {
		super.render(delta);
		
		// update screen logic
		this.update();
		if(System.currentTimeMillis() - lastBubble > 100)
		spawnBubule();
		
		// clear screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// update camera
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		// draw first batch (background)
		batch.begin();
		batch.draw(titleScreen, screen.x, screen.y, screen.width, screen.height);
		JeffPoulpe.draw(batch);
		batch.end();
		
		// draw second batch (UI & player selection)
		batch.enableBlending();
		batch.begin();
		font.getData().setScale(4);
		font.draw(batch, "Start", 1600, 800);
		font.draw(batch, "Exit", 1600, 700);
		batch.draw(selectorTexture, selector.x, selectorYPositions[selectorPosition] - selector.height / 2, selector.width, selector.height);
		for(Bulle b : bulles) {
			b.draw(batch, 1.0f);
		}
		for(Player p : Player.getPlayers()) {
			p.draw(batch, 1.0f);
		}
		batch.end();
	}
	
	public void update() {
		updateControls();
	}
	
	public void updateControls() {
		// Player amount controls
		// Selector controls
		if(mainController.getAxis(mainController.getMapping().axisLeftY) > 0.5 && System.currentTimeMillis() - lastAction >= 150) {
			selectorPosition = (selectorPosition == 0) ? selectorPossibilities - 1 : selectorPosition - 1;
			lastAction = System.currentTimeMillis();
			selectionMove.play();
		}
		if(mainController.getAxis(mainController.getMapping().axisLeftY) < -0.5 && System.currentTimeMillis() - lastAction >= 150) {
			selectorPosition = (selectorPosition == selectorPossibilities - 1) ? 0 : selectorPosition + 1;
			lastAction = System.currentTimeMillis();
			selectionMove.play();
		}
		Iterator<Bulle> li = bulles.iterator();
		Bulle b;
		while(li.hasNext()) {
			b = li.next();
			b.move(2.0f);
			if(b.getSprite().getY() > 1500)
				li.remove();
		}
	}
	
	public void addPlayer() {
		Player p = new Player();
		if(Player.getPlayers().size() <= GameWorld.getControllers().size()) {
			p.setController(GameWorld.getControllers().get(Player.getPlayers().size() - 1));
		}
		Texture t = Player.getPlayerTextures().get(Player.getPlayers().size() - 1);
		Sprite s = new Sprite(t);
		s.setScale(0.2f);
		s.setCenterX(((float) Gdx.graphics.getWidth() / (float) (1 + playerCount)) * (Player.getPlayers().size() + 1));
		s.setCenterY(200.0f);
		p.setPlayerSprite(s);
		int i = 0;
		for(Player player : Player.getPlayers()) {
			s = player.getPlayerSprite();
			s.setCenterX(((float) Gdx.graphics.getWidth() / (float) (1 + playerCount)) * (i + 1));
			i++;
			player.setPlayerSprite(s);
		}
	}
	
	public void spawnBubule() {
		lastBubble = System.currentTimeMillis();
		Random r = new Random();
		Bulle b = new Bulle(r.nextInt(1920));
		bulles.add(b);
	}
	
	public void removePlayer() {
		Player.removeLastPlayer();
		int i = 0;
		for(Player player : Player.getPlayers()) {
			Sprite s = player.getPlayerSprite();
			s.setCenterX(((float) Gdx.graphics.getWidth() / (float) (1 + playerCount)) * (i + 1));
			i++;
			player.setPlayerSprite(s);
		}
	}
	
	public void dispose() {
		mainController.removeListener(inputController);
	}

}
