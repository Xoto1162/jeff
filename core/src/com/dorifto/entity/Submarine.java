package com.dorifto.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Submarine extends Actor {

	private Texture[] textures;
	private int currentTexture;
	private Sprite sprite;
	private List<Bulle> bulles;
	
	private long lastBubble;
	private long lastFrame;
	
	public Submarine() {
		textures = new Texture[]{
				new Texture(Gdx.files.internal("Submarine/submarine_0.png")),
				new Texture(Gdx.files.internal("Submarine/submarine_1.png")),
				new Texture(Gdx.files.internal("Submarine/submarine_2.png"))
				};
		currentTexture = 0;
		sprite = new Sprite(textures[currentTexture]);
		bulles = new LinkedList<Bulle>();
		lastBubble = System.currentTimeMillis();
		lastFrame = System.currentTimeMillis();
	}
	
	@Override
	public void draw(Batch batch, float alpha) {
		sprite.draw(batch);
		Random r = new Random();
		float entropia = 40f;
		if(System.currentTimeMillis() - lastBubble > 50 + 100 * r.nextFloat()) {
			bulles.add(
					new Bulle(
							sprite.getX() + sprite.getWidth() - sprite.getWidth() * sprite.getScaleX() + r.nextFloat() * entropia,
							sprite.getY() + sprite.getHeight() - sprite.getHeight() * sprite.getScaleY() - 130 + r.nextFloat() * entropia
					)
				);
			lastBubble = System.currentTimeMillis();
		}
		ListIterator<Bulle> it = bulles.listIterator();
		while(it.hasNext()) {
			Bulle b = it.next();
			if (b.getSprite().getY() > 1080)
				it.remove();
			b.move(5.0f);
			b.draw(batch, alpha);
		}
		if (System.currentTimeMillis() - lastFrame > 50) {
			currentTexture = (currentTexture + 1) % textures.length;
			sprite.setTexture(textures[currentTexture]);
			lastFrame = System.currentTimeMillis();
		}
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}
	
}
 