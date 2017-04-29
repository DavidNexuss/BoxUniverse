package com.nsoft.boxuniverse.world;

import org.lwjgl.opengl.GL;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.jcraft.jorbis.Block;
import com.nsoft.boxuniverse.main.Game;
import com.nsoft.boxuniverse.misc.BaseMaterial;
import com.nsoft.boxuniverse.world.BaseBlock.BlockDefinition;
/**
 * 
 * @author DavidNexus
 * 
 * Controls:
 * 
 * # (+) Add 1 layer counter
 * # (-) Remove 1 frome layer counter
 * # (P) Add 1 to materials counter
 * # (O) Remove 1 to materials counter
 */
public class BaseWorld implements InputProcessor {


	public WorldDefinition def;
	Layer[] layers;
	
	PerspectiveCamera cam;
	SpriteBatch batch;
	Texture background;
	ModelBatch b;
	static Environment e;
	static int layerSelector = 0;
	static int MaterialSelector = 0;
	static Thread InputManager;
	
	public BaseWorld(WorldDefinition def,boolean needGeneration) {
		
		this.def = def;
		layers = new Layer[def.NumLayers];
		
		batch = new SpriteBatch();
		background = new Texture(def.BackGroundPath);
		
		b = new ModelBatch();
		 cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        cam.position.set(0, 0, 10);
	        cam.lookAt(0,0,0);
	        cam.near = 1f;
	        cam.far = 300f;
	        cam.update();
	        
	     e = new Environment();
	     e.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
	     e.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	     
	     for (int i = 0; i < layers.length; i++) {
			
	    	 layers[i] = new Layer(i,needGeneration);
		}
	     
	   
	    // debug();
	}
	
	public void render(){
		
		/*
		 * Rendering background
		 */
		batch.begin();
		//	batch.setProjectionMatrix(cam.projection);
			
		batch.draw(background, 0, 0);
		batch.end();

		/*
		 * Renders map
		 */
		b.begin(cam);
		for (Layer layer : layers) {
			layer.render(b);
		}
	
		b.end();
		
		
		batch.begin();
		batch.draw(BaseMaterial.load(BaseMaterial.getNameFromIndex(MaterialSelector)).t, 20, Gdx.graphics.getHeight() - 20 - 64, 64, 64);
		batch.end();
		/*
		 * Test camera controls
		 */
		Controls.CamTest(cam,Gdx.graphics.getDeltaTime());
	}
	
	/**
	 * For debugging only
	 */
	public void debug(){
		
		BaseBlock.BlockDefinition a = new BaseBlock.BlockDefinition();
     	a.material = BaseMaterial.load(BaseMaterial.getNameFromIndex((int)(Math.random() * BaseMaterial.getListSize())));
		a.depth  = 1;
		a.width = 1;
		a.height = 1;
	
		a.X = (int) (Math.random()*10) -5;
		a.Y = (int) (Math.random()*10) -5;
		
		int index = (int) (Math.random()*def.NumLayers);
		a.world = layers[index].mundo;
		layers[index].addNewBlock(a);

	}
	
	public void debug(int x, int y){
		BaseBlock.BlockDefinition a = new BaseBlock.BlockDefinition();
     	a.material = BaseMaterial.load(BaseMaterial.getNameFromIndex(MaterialSelector));
		a.depth  = 1;
		a.width = 1;
		a.height = 1;
		Ray ray = cam.getPickRay(x*2 - Gdx.graphics.getWidth()/2 + cam.position.x*2, y*2 - Gdx.graphics.getHeight()/2 + cam.position.y*2);
		Vector3 pos = new Vector3();
		a.X = (int) ray.getEndPoint(pos, -ray.origin.z / ray.direction.z).x;
		a.Y =  (int) ray.getEndPoint(pos, -ray.origin.z / ray.direction.z).y;

		a.world = layers[layerSelector].mundo;
		layers[layerSelector].addNewBlock(a);
		
	}

	@Override
	public boolean keyDown(int keycode) {
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if(keycode == Keys.R){
			
			for (int i = 0; i < layers.length; i++) {
				
				layers[i] = new Layer(i,true);
			}
		}
		else if(keycode == Keys.PLUS){
			
			if(layerSelector + 1 < layers.length) layerSelector++;
		}
		else if(keycode == Keys.MINUS){
			
			if(layerSelector > 0)layerSelector--;
		}
		else if(keycode == Keys.P){
			
			if(MaterialSelector < BaseMaterial.getListSize()-1) MaterialSelector++;
		}
		else if(keycode == Keys.O){
			
			if(MaterialSelector > 0) MaterialSelector--;
		}
		
		else if(keycode == Keys.L){
			
			foreach((b) ->{
				
				b.saveStatus();
			});
			WorldLoader.save();
		}
		
		else if(keycode == Keys.Y){
			
			Game.LoadMap("src/com/nsoft/boxuniverse/resources/maps/map1.map");
		}
		return true;
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
		debug(screenX,screenY);
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
		
		cam.translate(0, 0, amount*((cam.position.z + amount/2)/(cam.position.z)));
		cam.update();
		return true;
	}

	public void handleBlockCreation(BlockDefinition value,String name) {
		
		value.world = layers[(int) value.Z].mundo;
		layers[(int) value.Z].addLoadedBlock(value,name);
	}
	
	public void foreach(Act act){
		
		for (Layer layer : layers) {
			
			layer.foreach(act);
		}
	}
}
