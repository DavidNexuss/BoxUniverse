package com.nsoft.boxuniverse.main;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Main {

	static Lwjgl3Application app;
	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(800, 600);
		config.setTitle("Box Universe");
		config.useOpenGL3(true, 3, 2);
		app = new Lwjgl3Application(new Game(), config);
	}

}
