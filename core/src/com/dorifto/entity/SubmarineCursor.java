package com.dorifto.entity;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.dorifto.entity.player.Player;
import com.dorifto.stage.Submarine;

public class SubmarineCursor extends Actor {
	private static ArrayList<SubmarineCursor> cursors = new ArrayList<SubmarineCursor>();
	private static ArrayList<Texture> textures = new ArrayList<Texture>();
	
	private Controller controller;
	private ControllerAdapter inputController;
	
	private Player associatedPlayer;
	private Sprite cursorSprite;
	
	private int score;
	
	public SubmarineCursor(Player player) {
		this.score = 0;
		this.controller = player.getController();
		System.out.println(player.getPlayerName());
		this.associatedPlayer = player;
		cursors.add(this);
		inputController = new ControllerAdapter() {
			public boolean buttonDown(Controller controller, int buttonCode) {
				Iterator<Trou> li = Submarine.trous.iterator();
				while(li.hasNext()) {
					Trou t = (Trou) li.next();
					if(cursorSprite.getBoundingRectangle().overlaps(t.getTrouSprite().getBoundingRectangle())){
						Sound bitocul = Gdx.audio.newSound(Gdx.files.internal("sounds/selection_yes.wav"));
						bitocul.play();
						PlaqueMetal mp = new PlaqueMetal();
						Sprite s = new Sprite(new Texture(Gdx.files.internal("Submarine/plaque_metal_seule.png")));
						s.setScale(0.5f);
						float x = t.getTrouSprite().getX();
						float y = t.getTrouSprite().getY();
						s.setCenter(x + 80f, y + 80f);
						mp.setSprite(s);
						Submarine.plaques.add(mp);
						li.remove();
						score++;
					}
				}
					return false;
			}
		};
		controller.addListener(inputController);
	}
	
	public void recordScore() {
		this.associatedPlayer.addScore(this.score);
	}
	
	
	/**
	 * Updates the logic of the player
	 */
	public void update() {
		float accel = 1000.0f;
		if(this.controller.getAxis(controller.getMapping().axisLeftX) > 0.2 && this.cursorSprite.getX() < 1920 - this.cursorSprite.getHeight() / 2 -50)
			this.cursorSprite.setX(this.cursorSprite.getX() + accel * this.controller.getAxis(controller.getMapping().axisLeftX) * Gdx.graphics.getDeltaTime());
		if(this.controller.getAxis(controller.getMapping().axisLeftX) < -0.2 && this.cursorSprite.getX() > 0 - this.cursorSprite.getHeight() / 2 +50)
			this.cursorSprite.setX(this.cursorSprite.getX() + accel * this.controller.getAxis(controller.getMapping().axisLeftX) * Gdx.graphics.getDeltaTime());
		if(this.controller.getAxis(controller.getMapping().axisLeftY) > 0.2 && this.cursorSprite.getY() > 0 - this.cursorSprite.getWidth() / 2 + 50)
			this.cursorSprite.setY(this.cursorSprite.getY() - accel * this.controller.getAxis(controller.getMapping().axisLeftY) * Gdx.graphics.getDeltaTime());
		if(this.controller.getAxis(controller.getMapping().axisLeftY) < -0.2 && this.cursorSprite.getY() < 1010 - this.cursorSprite.getWidth() / 2)
			this.cursorSprite.setY(this.cursorSprite.getY() - accel * this.controller.getAxis(controller.getMapping().axisLeftY) * Gdx.graphics.getDeltaTime());
	}
	
	/**
	 * Draws the sprite using batch
	 * @param batch
	 */
	@Override
	public void draw(Batch batch, float alpha) {
		this.cursorSprite.draw(batch);
	}
	
	public int getScore() {
		return this.score;
	}
	
	public ControllerAdapter getInputController() {
		return inputController;
	}
	
	/**
	 * Set the sprite associated with this player to sprite
	 * @param sprite
	 */
	public void setCursorSprite(Sprite sprite) {this.cursorSprite = sprite;}
	
	
	/**
	 * Return the sprite associated with this player
	 * @return playerSprite
	 */
	public Sprite getCursorSprite() {return this.cursorSprite;}
	
	/**
	 * Set the controller associated with this player to c
	 * @param c
	 */
	public void setController(Controller c) {
		this.controller = c;
		System.out.println(this.associatedPlayer.getPlayerName() + " got controller : " + c.getName());
	}
	
	/**
	 * Return the controller associated with this player
	 * @return controller
	 */
	public Controller getController() {return this.controller;}
	 
	/**
	 * Return the name associated with this player
	 * @return playerName
	 */
	public Player getAssociatedPlayer() {return this.associatedPlayer;}
	
	public static void loadPlayerTextures() {
		for(int i=0; i< Player.getPlayers().size();i++) {
			textures.add(new Texture(Gdx.files.internal("PlayerAssets/cible_"+(i+1)+".png")));
		}
	}
	
	// STATIC ACCESSORS
	
	/**
	 * Return an ArrayList containing all the players
	 * @return players
	 */
	public static ArrayList<SubmarineCursor> getCursors() {return cursors;}
	public static ArrayList<Texture> getTextures() {return textures;}
}
