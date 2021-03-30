package com.dorifto.entity;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bulle extends Actor{
	Texture texture = new Texture(Gdx.files.internal("Submarine/bulle.png"));
	Sprite sprite;
	
	public Bulle(float x) {
		this.sprite = new Sprite(texture);
		sprite.setX(x - 20f);
		Random r = new Random();
		sprite.setScale(r.nextFloat() * 0.4f);
	}
	
	public Bulle(float x, float y) {
		this(x);
		sprite.setY(y);
	}
	
	@Override
    public void draw(Batch batch, float alpha){
        sprite.draw(batch);
    }
	
	public void move(float speed) {
		Random r = new Random();
		this.sprite.setY(this.sprite.getY() + speed * r.nextFloat() + 2.0f);
	}
	
	public Sprite getSprite() {
		return this.sprite;
	}

}
