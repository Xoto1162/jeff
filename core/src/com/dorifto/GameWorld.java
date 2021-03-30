package com.dorifto;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.dorifto.entity.player.Player;
import com.dorifto.screen.MenuScreen;
import com.dorifto.screen.ScreenManager;
import com.dorifto.stage.*;


public class GameWorld extends Game {

	private static ArrayList<Controller> controllers;
	private static LinkedList<LevelStage> stages;

	private static int playerCount;

	@Override
	public void create() {
		controllers = new ArrayList<Controller>();
		stages = new LinkedList<LevelStage>();
		loadTextures();

		playerCount = 0;

		stages.add(new SubmarinePre());
		stages.add(new Submarine());
		stages.add(new SubmarinePost());
		stages.add(new RunnerPre());
		stages.add(new Runner());
		stages.add(new RunnerPost());
		//stages.add(new SquidDodge());
		stages.add(new ArenaPre());
		stages.add(new Arena());
		stages.add(new ArenaPost());

		// get all the controllers plugged in the computer
		for (Controller controller : Controllers.getControllers()) {
			Gdx.app.log("controller", "detected controller : " + controller.getName());
			if (true) { //Xbox.isXboxController(controller)
				controllers.add(controller);
				Gdx.app.log("controller", "added controller : " + controller.getName());
			}
		}

		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen(new MenuScreen());
	}

	public static void setPlayerCount(int count) {
		playerCount = count;
	}

	public static int getPlayerCount() {
		return playerCount;
	}

	public void loadTextures() {
		Player.loadPlayerTextures();
	}

	public static ArrayList<Controller> getControllers() {return controllers;}

	public static LinkedList<LevelStage> getStages() {return stages;}

}