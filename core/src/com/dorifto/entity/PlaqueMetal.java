package com.dorifto.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlaqueMetal extends Actor{
	Texture texture = new Texture(Gdx.files.internal("Submarine/plaque_metal_seule.png"));
	private Sprite Sprite;
		
		@Override
	    public void draw(Batch batch, float alpha){
	        Sprite.draw(batch);
	    }

		public void setSprite(Sprite s) {
			this.Sprite = s;
		}
		
		public Sprite getTrouSprite() {
			return this.Sprite;
		}
		
		public void updateTexture() {
			texture = new Texture(Gdx.files.internal("Submarine/plaque_metal.png"));
			Sprite.setTexture(texture);
		}
		
}
