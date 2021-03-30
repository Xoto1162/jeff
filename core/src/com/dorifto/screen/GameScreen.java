package com.dorifto.screen;

import java.util.List;

import com.dorifto.stage.LevelStage;

public class GameScreen extends AbstractScreen {
	
	private List<LevelStage> stages;
	private LevelStage currentStage;
	
	public GameScreen(List<LevelStage> stages) {
		this.stages = stages;
		this.currentStage = this.stages.get(0);
		this.stages.remove(0);
	}

	@Override
	public void buildStage() {
		this.currentStage.buildStage();
	}
	
	public void render(float delta) {
		super.render(delta);
		this.currentStage.draw();
		if(this.currentStage.isWon)
			nextStage();
	}
	
	/**
	 * Indique s'il reste encore des minis jeux
	 * @return
	 */
	private boolean hasNextStage() {
		return this.stages.size() > 0;
 	}
	
	/**
	 * Passe au mini jeu suivant
	 */
	private void nextStage() {
		if (this.hasNextStage()) {
			this.currentStage.dispose();
			this.currentStage = this.stages.get(0);
			this.stages.remove(0);
			this.buildStage();
		}
	}

}
