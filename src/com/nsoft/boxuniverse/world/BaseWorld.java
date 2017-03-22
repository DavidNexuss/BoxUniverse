package com.nsoft.boxuniverse.world;

import org.lwjgl.opengl.GL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.nsoft.boxuniverse.misc.BaseMaterial;

public class BaseWorld implements InputProcessor {

	int LayerNumber;
	Layer[] layers;
	
	PerspectiveCamera cam;
	ModelBatch b;
	static Environment e;
	static Thread InputManager;
	
	public BaseWorld(int numlayers) {
		
		LayerNumber = numlayers;
		layers = new Layer[numlayers];
		
		b = new ModelBatch();
		 cam = new PerspectiveCamera(50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        cam.position.set(0, 0, 10);
	        cam.lookAt(0,0,0);
	        cam.near = 1f;
	        cam.far = 300f;
	        cam.update();
	        
	     e = new Environment();
	     e.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
	     e.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	     
	     for (int i = 0; i < layers.length; i++) {
			
	    	 layers[i] = new Layer(i);
		}
	     
	   
	     debug();
	}
	
	public void render(){
		
		b.begin(cam);
		for (Layer layer : layers) {
			layer.render(b);
		}
		b.end();
		
		Controls.CamTest(cam,Gdx.graphics.getDeltaTime());
	}
	
	/**
	 * For debugging only
	 */
	public void debug(){
		
		BlockDefinition a = new BlockDefinition();
     	a.material = BaseMaterial.load(BaseMaterial.getNameFromIndex((int)(Math.random() * BaseMaterial.getListSize())));
		a.depth  = 1;
		a.width = 1;
		a.height = 1;
	
		a.X = (int) (Math.random()*10) -5;
		a.Y = (int) (Math.random()*10) -5;
		
		int index = (int) (Math.random()*LayerNumber);
		a.world = layers[index].mundo;
		layers[index].addNewBlock(a);

	}

	@Override
	public boolean keyDown(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		debug();
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		
		cam.translate(0, 0, amount);
		cam.update();
		return true;
	}
}
