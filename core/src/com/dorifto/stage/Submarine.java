package com.dorifto.stage;

import java.util.ArrayList;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.dorifto.entity.Trou;
import com.dorifto.entity.PlaqueMetal;
import com.dorifto.entity.SubmarineCursor;
import com.dorifto.entity.player.Player;
import com.dorifto.GameWorld;

public class Submarine extends LevelStage {
	
	private Texture background;
	public static ArrayList<Trou> trous;
	public static LinkedList<PlaqueMetal> plaques;
	public static LinkedList<PlaqueMetal> plaques2;
	private long lastHole = 0;
	public static Player winner;
	
	private Music submarineMusic;
	
	public void buildStage() {
		background = new Texture(Gdx.files.internal("Backgrounds/Submarine_background.png"));
		SubmarineCursor.loadPlayerTextures();
		winner = null;
		trous = new ArrayList<Trou>();
		plaques = new LinkedList<PlaqueMetal>();
		plaques2 = new LinkedList<PlaqueMetal>();
		for(Player p: Player.getPlayers()) {
			SubmarineCursor c = new SubmarineCursor(p);
			Texture t = SubmarineCursor.getTextures().get(SubmarineCursor.getCursors().size() - 1);
			Sprite s = new Sprite(t);
			s.setScale(0.3f);
			s.setCenterX(Gdx.graphics.getWidth() / 2.0f);
			s.setCenterY(200.0f);
			c.setCursorSprite(s);
		}
		submarineMusic = Gdx.audio.newMusic(Gdx.files.internal("submarine_music.mp3"));
		submarineMusic.setLooping(true);
		submarineMusic.play();
	}
	
	public void draw() {
		update();
		
		batch.begin();
		batch.draw(background, screen.x, screen.y, screen.width, screen.height);
		batch.end();
		
		batch.begin();
		for(PlaqueMetal p : plaques2) {
			p.draw(batch, 1.0f);
		}
		batch.end();
		
		batch.begin();
		for(PlaqueMetal pm : plaques) {
			pm.draw(batch, 1.0f);
		}
		for(Trou t: trous) {
			t.draw(batch, 1.0f);
		}
		batch.end();
		
		batch.begin();
		for(SubmarineCursor sc : SubmarineCursor.getCursors()) {
			sc.draw(batch, 1.0f);
		}
		batch.end();
		
		batch.begin();
		int i = 0;
		for(SubmarineCursor sc : SubmarineCursor.getCursors()) {
			switch(i) {
				case 0:
					font.setColor(Color.RED);
					break;
				case 1:
					font.setColor(Color.PURPLE);
					break;
				case 2:
					font.setColor(Color.GREEN);
					break;
				case 3:
					font.setColor(Color.YELLOW);
					break;
			}
			font.draw(batch, "Score : " + sc.getScore(), ((i + 1)%2 == 1) ? 100 : 1700, (i > 1) ? 100 : 980);
			i++;
			font.setColor(Color.BLACK);
		}
		batch.end();
	}
	
	public void update() {
		for(SubmarineCursor sc : SubmarineCursor.getCursors()) {
			sc.update();
			if(sc.getScore() >= 30) {
				for(SubmarineCursor scr : SubmarineCursor.getCursors()) {
					scr.recordScore();
					scr.getController().removeListener(scr.getInputController());
				}
				this.isWon = true;
				Sound win = Gdx.audio.newSound(Gdx.files.internal("game_won.wav"));
				winner = sc.getAssociatedPlayer();
				win.play(0.5f);
				System.out.println("Gagnant Submarine : "+sc.getAssociatedPlayer().getPlayerName());
			}
		}
		if(System.currentTimeMillis() - lastHole >= (750 / GameWorld.getPlayerCount())) {
			if(plaques.size()>15) {
				plaques.getFirst().updateTexture();
				plaques2.addLast(plaques.getFirst());
				plaques.removeFirst();

				if(plaques2.size()>20) {
					plaques2.removeFirst();
				}
			}
			spawnTrou();
			if(!plaques2.isEmpty()) {
				plaques2.removeFirst();
			}
		}
		for(Trou t : trous) {
			Sprite s = t.getJetSprite();
			s.setRotation(s.getRotation() + 1.0f);
		}
	}
	
	public void spawnTrou() {
		Trou t = new Trou();
		Sprite trou = new Sprite(new Texture(Gdx.files.internal("Submarine/trou.png")));
		Sprite jet = new Sprite(new Texture(Gdx.files.internal("Submarine/jet.png")));
		trou.setScale(0.6f);
		float x = (float) (100.0f+Gdx.graphics.getWidth()-(Math.random()*Gdx.graphics.getWidth()));
		float y = (float) (100.0f+Gdx.graphics.getHeight()-(Math.random()*Gdx.graphics.getHeight()));
		trou.setCenter(x, y);
		t.setSpriteTrou(trou);
		jet.setScale(0.6f);
		jet.setCenter(x, y);
		t.setJetSprite(jet);
		
		trous.add(t);
		lastHole = System.currentTimeMillis();
	}
	
	public void dispose() {
		System.out.println("Conchita dispose");
		for (Player p : Player.getPlayers()) {
			p.removeControl();
		}
	}
	
}
