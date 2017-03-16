package com.nsoft.boxuniverse.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.nsoft.boxuniverse.world.BaseActor;
import com.nsoft.boxuniverse.world.GameStage;

public class Game extends ApplicationAdapter {

	GameStage stage;
	@Override
	public void create() {
		
		stage = new GameStage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		stage.debug();
		Gdx.gl.glClearColor(0, 0, 0, 1);
	}
	
	@Override
	public void render() {
		
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void dispose() {
		
	}
	
}
