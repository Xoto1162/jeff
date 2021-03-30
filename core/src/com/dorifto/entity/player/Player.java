package com.dorifto.entity.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.dorifto.stage.Arena;

public class Player extends Actor{

	private static ArrayList<Player> players = new ArrayList<Player>();
	private static ArrayList<Texture> textures = new ArrayList<Texture>();
	private static ArrayList<Texture> texturesNage = new ArrayList<Texture>();
	
	private Controller controller;
	private ControllerAdapter inputControl;
	private boolean nage;
	
	private String playerName;
	private Sprite playerSprite;
	
	private int score;
	
	private boolean isDead;
	private int deathCount;
	private int playerID;
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	public static Vector2[] ArenaSpawns = {
			new Vector2(280, 220),
			new Vector2(1050, 220),
			new Vector2(280, -130),
			new Vector2(1050, -130)
	};
	
	public Player() {
		this.playerName = "P" + (players.size() + 1);
		playerID = players.size();
;		players.add(this);
		this.score = 0;
		this.deathCount = 0;
		this.isDead = false;
		this.nage = false;
	}
	
	public void addScore(int i) {
		this.score += i;
		System.out.println("Score of " + this.getPlayerName() + " is : " + this.score);
	}
	
	
	/**
	 * Updates the logic of the player
	 */
	public void update() {
		if(this.isDead)
			return;
		if(Arena.playerIsOutOfBounds(this)) {
			int index = players.indexOf(this);
			this.playerSprite.setX(ArenaSpawns[index].x);
			this.playerSprite.setY(ArenaSpawns[index].y);
		}
		if(Math.abs(this.controller.getAxis(controller.getMapping().axisLeftX)) > Math.abs(this.controller.getAxis(controller.getMapping().axisLeftY))) {
			if(this.controller.getAxis(controller.getMapping().axisLeftX) > 0.15f)
				this.playerSprite.setRotation(270);
			else if (this.controller.getAxis(controller.getMapping().axisLeftX) < -0.15f)
				this.playerSprite.setRotation(90);
		} else {
			if(this.controller.getAxis(controller.getMapping().axisLeftY) > 0.15f)
				this.playerSprite.setRotation(180);
			else if (this.controller.getAxis(controller.getMapping().axisLeftY) < -0.15f)
				this.playerSprite.setRotation(0);
		}
		if(this.controller.getAxis(controller.getMapping().axisLeftX) > 0.2)
			this.playerSprite.setX(this.playerSprite.getX() + 5.0f * this.controller.getAxis(controller.getMapping().axisLeftX));
		if(this.controller.getAxis(controller.getMapping().axisLeftX) < -0.2)
			this.playerSprite.setX(this.playerSprite.getX() + 5.0f * this.controller.getAxis(controller.getMapping().axisLeftX));
		if(this.controller.getAxis(controller.getMapping().axisLeftY) > 0.2)
			this.playerSprite.setY(this.playerSprite.getY() - 5.0f * this.controller.getAxis(controller.getMapping().axisLeftY));
		if(this.controller.getAxis(controller.getMapping().axisLeftY) < -0.2)
			this.playerSprite.setY(this.playerSprite.getY() - 5.0f * this.controller.getAxis(controller.getMapping().axisLeftY));
		if(this.controller.getButton(controller.getMapping().buttonA)){
			for(Player p : players) {
				if(p.equals(this))
					continue;
				if(p.getPlayerSprite().getBoundingRectangle().overlaps(this.playerSprite.getBoundingRectangle())) {
					p.die();
				}
			}
		}
	}
	
	public void die() {
		System.out.println(this.getPlayerName() + " died");
		Sound s = Gdx.audio.newSound(Gdx.files.internal("goeland.wav"));
		s.play();
		int index = players.indexOf(this);
		this.playerSprite.setX(ArenaSpawns[index].x);
		this.playerSprite.setY(ArenaSpawns[index].y);
		this.deathCount++;
		if(this.deathCount >= 3) {
			this.isDead = true;
			this.playerSprite.setX(10000);
			this.playerSprite.setY(10000);
		}
	}
	
	public int getLifes() {
		return 3 - this.deathCount;
	}
	
	/**
	 * Draws the sprite using batch
	 * @param batch
	 */
	@Override
	public void draw(Batch batch, float alpha) {
		this.playerSprite.draw(batch);
	}
	
	/**
	 * Remove the last player from players
	 */
	public static void removeLastPlayer() {
		players.remove(players.size() - 1);
	}
	
	/**
	 * 
	 */
	public void setRunnerControl() {
		this.inputControl = new ControllerAdapter() {
			public boolean buttonDown(Controller controller, int buttonCode) {
				if (buttonCode == controller.getMapping().buttonA) {
					playerSprite.setY(playerSprite.getY() + 10.0f);
					if (nage) {
						playerSprite.setTexture(texturesNage.get(playerID));
					}
					else {
						playerSprite.setTexture(textures.get(playerID));
					}
					nage = !nage;
				}
				return false;
			}
		};
		this.controller.addListener(this.inputControl);
	}
	
	public void removeControl() {
		this.controller.removeListener(this.inputControl);
		this.inputControl = null;
		this.nage = false;
	}

	
	/**
	 * Set the sprite associated with this player to sprite
	 * @param sprite
	 */
	public void setPlayerSprite(Sprite sprite) {this.playerSprite = sprite;}
	
	
	/**
	 * Return the sprite associated with this player
	 * @return playerSprite
	 */
	public Sprite getPlayerSprite() {return this.playerSprite;}
	
	
	/**
	 * Set the controller associated with this player to c
	 * @param c
	 */
	public void setController(Controller c) {
		this.controller = c;
		System.out.println(this.getPlayerName() + " got controller : " + c.getName());
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
	public String  getPlayerName() {return this.playerName;}
	
	
	// STATIC ACCESSORS
	
	/**
	 * Return an ArrayList containing all the players
	 * @return players
	 */
	public static ArrayList<Player> getPlayers() {return players;}
	
	/**
	 * Return an ArrayList containing all player textures
	 * @return textures
	 */
	public static ArrayList<Texture> getPlayerTextures() {return textures;}
	
	// STATIC METHODS
	
	/**
	 * pre-loads all player textures into textures
	 */
	public static void loadPlayerTextures() {
		textures.add(new Texture(Gdx.files.internal("PlayerAssets/player_1.png")));
		textures.add(new Texture(Gdx.files.internal("PlayerAssets/player_2.png")));
		textures.add(new Texture(Gdx.files.internal("PlayerAssets/player_3.png")));
		textures.add(new Texture(Gdx.files.internal("PlayerAssets/player_4.png")));
		
		texturesNage.add(new Texture(Gdx.files.internal("PlayerAssets/player_1_nage.png")));
		texturesNage.add(new Texture(Gdx.files.internal("PlayerAssets/player_2_nage.png")));
		texturesNage.add(new Texture(Gdx.files.internal("PlayerAssets/player_3_nage.png")));
		texturesNage.add(new Texture(Gdx.files.internal("PlayerAssets/player_4_nage.png")));
	}

	public String getPlayerColor() {
		if(Player.getPlayers().get(0).equals(this))
			return "rouge";
		if(Player.getPlayers().get(1).equals(this))
			return "violet";
		if(Player.getPlayers().get(2).equals(this))
			return "vert";
		if(Player.getPlayers().get(3).equals(this))
			return "jaune";
		else
			return "Blanc : error";
	}
}
