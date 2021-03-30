package com.dorifto.stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.dorifto.entity.Bulle;
import com.dorifto.entity.player.Player;

public class Runner extends LevelStage {

	private Texture background;
	public static Player winner;
	
	private long lastBubble;
	private ArrayList<Bulle> bulles;
	
	@Override
	public void buildStage() {
		winner = null;
		background = new Texture(Gdx.files.internal("background_course_sous_marin.png"));
		for(Player p : Player.getPlayers()) {
			p.setRunnerControl();
		}
		this.lastBubble = System.currentTimeMillis();
		this.bulles = new ArrayList<Bulle>();
	}
	
	public void draw() {
		if(System.currentTimeMillis() - lastBubble > 100)
			spawnBubule();
		
		batch.enableBlending();
		batch.begin();
		batch.draw(background, screen.x, screen.y, screen.width, screen.height);
		for(Player p : Player.getPlayers()) {
			p.draw(batch, 1.0f);
			if (p.getPlayerSprite().getY() >= 375) {
				winner = p;
				System.out.println("Gagnant Runner : " + p.getPlayerName());
				isWon = true;
			}
		}
		for(Bulle b : bulles) {
			b.draw(batch, 1.0f);
		}
		batch.end();
		Iterator<Bulle> li = bulles.iterator();
		Bulle b;
		while(li.hasNext()) {
			b = li.next();
			b.move(2.0f);
			if(b.getSprite().getY() > 1500)
				li.remove();
		}
	}
	
	public void dispose() {
		for (Player p : Player.getPlayers()) {
			p.removeControl();
		}
	}
	
	public void spawnBubule() {
		lastBubble = System.currentTimeMillis();
		Random r = new Random();
		Bulle b = new Bulle(r.nextInt(1920));
		bulles.add(b);
	}
	
}
