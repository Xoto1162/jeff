package com.dorifto.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Trou extends Actor{
	
Texture texture = new Texture(Gdx.files.internal("Submarine/trou.png"));
	private Sprite spriteTrou;
	private Sprite spriteJet;
	
	@Override
    public void draw(Batch batch, float alpha){
        spriteTrou.draw(batch);
        spriteJet.draw(batch);
    }

	public void setSpriteTrou(Sprite s) {
		this.spriteTrou = s;
	}
	
	public Sprite getTrouSprite() {
		return this.spriteTrou;
	}
	
	public void setJetSprite(Sprite s) {
		this.spriteJet = s;
	}
	
	public Sprite getJetSprite() {
		return this.spriteJet;
	}

}
