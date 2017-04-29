package com.nsoft.boxuniverse.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.nsoft.boxuniverse.misc.BaseSound;
import com.nsoft.boxuniverse.world.BaseObject;
import com.nsoft.boxuniverse.world.BaseWorld;
import com.nsoft.boxuniverse.world.WorldDefinition;
import com.nsoft.boxuniverse.world.WorldLoader;


public class Game extends ApplicationAdapter {

	public static BaseWorld world;
	public static boolean isReady = false;
	public static Thread Main;
	public static Json MainJson = new Json();
	@Override
	public void create() {
		Init.LoadEverything();
		Main = Thread.currentThread();
		WorldDefinition def = new WorldDefinition();
		def.NumLayers = 3;
		def.BackGroundPath = "src/com/nsoft/boxuniverse/resources/materials/background.jpg";
		world = WorldLoader.loadNewMap(def);
		
		Gdx.input.setInputProcessor(world);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		isReady = true;
		
	}
	
	public static void LoadMap(String mapName){
		
		isReady = false;
		Init.LoadEverything();
		WorldLoader.load(mapName);
		world = null;
		world = WorldLoader.loadMap();
		
		Gdx.input.setInputProcessor(world);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		isReady = true;
	}
	@Override
	public void render() {
		
		if(isReady){
			 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
			  Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			world.render();
		}
		 
	}
	
	@Override
	public void resize(int width, int height) {
		
	}
	
	@Override
	public void dispose() {
		
	}
	
}
