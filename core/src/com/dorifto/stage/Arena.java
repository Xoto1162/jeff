package com.dorifto.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import com.dorifto.entity.player.Player;

public class Arena extends LevelStage {

	private Texture background;
	private Texture arena;
	public static Player winner;
	private long buildTime;
	@Override
	public void buildStage() {
		buildTime = System.currentTimeMillis();
		winner = null;
		background = new Texture(Gdx.files.internal("fond_eau_arene.png"));
		arena = new Texture(Gdx.files.internal("arene.png"));
		int i = 1;
		for (Player p : Player.getPlayers()) {
			Sprite s = p.getPlayerSprite();
			Texture t = new Texture(Gdx.files.internal("PlayerAssets/player_" + i++ + "_fight_first.png"));
			s.setTexture(t);
			s.setScale(0.1f);
			s.setX(Player.ArenaSpawns[i - 2].x);
			s.setY(Player.ArenaSpawns[i - 2].y);
			p.setPlayerSprite(s);
		}
	}

	public void draw() {
		update();
		batch.begin();
		batch.draw(background, screen.x, screen.y, screen.width, screen.height);
		batch.draw(arena, screen.x + 100, screen.y + 100, screen.width - 150, screen.height - 200);
		int i = 0;
		for (Player p : Player.getPlayers()) {
			p.draw(batch, 1.0f);
			switch (i) {
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
			font.draw(batch, "Vies Restantes : " + p.getLifes(), ((i + 1) % 2 == 1) ? 100 : 1700, (i > 1) ? 100 : 980);
			i++;
			font.setColor(Color.BLACK);
		}

		batch.end();
	}

	public static boolean playerIsOutOfBounds(Player p) {
		Rectangle r = new Rectangle();
		r.x = 150;
		r.y = 170;
		r.width = Gdx.graphics.getWidth() - 300;
		r.height = Gdx.graphics.getHeight() - 260;
		return !(r.contains(p.getPlayerSprite().getBoundingRectangle()));
	}

	public void update() {
		int alives = 0;
		for (Player p : Player.getPlayers()) {
			p.update();
			alives = (p.getLifes()>=1)? alives+1 : alives;
		}
		if(alives == 1) {
			for (Player p : Player.getPlayers()) {
				if(p.getLifes()>= 1) {
					if(Player.getPlayers().size() != 1) {
						winner = p;
						isWon = true;
					}
					else {
						if(System.currentTimeMillis()-buildTime > 20000) {
							winner = Player.getPlayers().get(0);
							isWon = true;
						}
					}
				}
			}
		}
	}

}
