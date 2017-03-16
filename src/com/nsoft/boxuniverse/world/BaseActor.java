package com.nsoft.boxuniverse.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseActor extends Actor {

	private Texture tex;
	public BaseActor(Texture a) {
		
		tex = a;
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {

	 if(tex != null)batch.draw(tex, getX(), getY(), getWidth(), getHeight());
		
	}

	
}
