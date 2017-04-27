package com.nsoft.boxuniverse.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.nsoft.boxuniverse.misc.BaseSound;
import com.nsoft.boxuniverse.world.BaseWorld;


public class Game extends ApplicationAdapter {

	public BaseWorld world;
	public static Json MainJson = new Json();
	
	@Override
	public void create() {
		
		Init.LoadEverything();
		
		world = new BaseWorld(3);
		Gdx.input.setInputProcessor(world);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
	}
	
	@Override
	public void render() {
		
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		  Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		world.render();
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void dispose() {
		
	}
	
}
