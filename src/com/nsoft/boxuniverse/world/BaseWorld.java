package com.nsoft.boxuniverse.world;

import org.lwjgl.opengl.GL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

public class BaseWorld implements InputProcessor {

	Layer[] layers = new Layer[2];
	
	PerspectiveCamera cam;
	ModelBatch b;
	static Environment e;
	
	public BaseWorld() {
		
		b = new ModelBatch();
		 cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        cam.position.set(0, 3, 10);
	        cam.lookAt(5,5,0);
	        cam.near = 1f;
	        cam.far = 300f;
	        cam.update();
	        
	     e = new Environment();
	     e.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
	     e.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	     
	     layers[0] = new Layer(0);
	     layers[1] = new Layer(1);
	     debug();
	}
	
	public void render(){
		
		b.begin(cam);
		for (Layer layer : layers) {
			layer.render(b);
		}
		b.end();
	}
	
	/**
	 * For debugging only
	 */
	public void debug(){
		
		BlockDefinition a = new BlockDefinition();
		a.color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
		a.depth  = 1;
		a.width = 1;
		a.height = 1;
	
		a.X = (int) (Math.random()*10);
		a.Y = (int) (Math.random()*10);
				
		layers[(int) (Math.random()*2)].addNewBlock(a);
		
		new Thread(()->{
			
			while(true){
				
				try {
					
					System.out.println(Gdx.graphics.getFramesPerSecond());
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		debug();
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
