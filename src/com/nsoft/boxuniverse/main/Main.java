package com.nsoft.boxuniverse.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.nsoft.boxuniverse.misc.BaseMaterial;
import com.nsoft.boxuniverse.world.BaseBlock;

public class Main {

	static Lwjgl3Application app;
	public static void main(String[] args) {
		
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
	//	config.setWindowedMode(1280, 720);
		config.setFullscreenMode( Lwjgl3ApplicationConfiguration.getDisplayMode());
		config.setTitle("Box Universe");
		config.useOpenGL3(true, 3, 2);
		app = new Lwjgl3Application(new Game(), config);
	}
	
	public static void debug(){

	}

}
