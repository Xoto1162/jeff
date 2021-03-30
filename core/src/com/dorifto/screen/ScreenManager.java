package com.dorifto.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class ScreenManager {

	private static ScreenManager instance;
	
	private Game game;
	
	private ScreenManager() {
		
	}
	
	/**
	 * Retourne l'instance unique du ScreenManager
	 * @return
	 */
	public static ScreenManager getInstance() {
		if (instance == null) {
			instance = new ScreenManager();
		}
		return instance;
	}
	
	/**
	 * Initialise le ScreenManafer avec la Game
	 * @param game
	 */
	public void initialize(Game game) {
		this.game = game;
	}
	
	/**
	 * Change le screen � afficher
	 * @param screen
	 */
	public void showScreen(AbstractScreen screen) {
		Screen currentScreen = this.game.getScreen();
		
		screen.buildStage();
		game.setScreen(screen);
		
		if (currentScreen != null) {
			currentScreen.dispose();
		}
	}
	
}
