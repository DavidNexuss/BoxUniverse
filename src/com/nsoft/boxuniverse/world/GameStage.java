package com.nsoft.boxuniverse.world;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameStage extends Stage {

	public GameStage(Viewport vp) {
		
	//	setViewport(vp);
		setDebugAll(true);
	}
	
	@Override
	public void draw() {
		
		super.draw();
	}
	
	@Override
	public void act() {
	
		super.act();
	}
	
	public void debug(){
		
		BaseActor b = new BaseActor(null);

		b.setPosition(20, 20);
		b.setSize(20, 20);
		b.setDebug(true);
		
		addActor(b);
	}
}
