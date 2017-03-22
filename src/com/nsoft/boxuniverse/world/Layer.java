package com.nsoft.boxuniverse.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.nsoft.boxuniverse.misc.BaseMaterial;

public class Layer {
	
	int Z;
	static Vector2 gravity = new Vector2(0, -9.8f);
	static BlockDefinition ground;
	public World mundo;
	Box2DDebugRenderer renderer;
	private ArrayList<BaseObject> objects;
	
	
	public Layer(int z) {
		
		renderer = new Box2DDebugRenderer();
		Z = z;
		mundo = new World(gravity , true);
		objects = new ArrayList<>();
		
		ground.world = mundo;
		addNewBlock(ground);
	}
	
	public void addObject(BaseObject B){
		
		objects.add(B);
	}
	
	public void addNewBlock(BlockDefinition B){
		
		objects.add(new BaseBlock(B, Z));
	}
	public void render(ModelBatch mb){
		
		mundo.step(Gdx.graphics.getDeltaTime(), 6, 2);
		for (BaseObject baseObject : objects) {
			
			baseObject.draw(mb);
		}
		
		renderer.render(mundo, mb.getCamera().projection);
	}
	
	public static void init(){
		
		ground = new BlockDefinition();
		ground.material = BaseMaterial.load("Brick");
		ground.depth  = 1;
		ground.width = 20;
		ground.height = 1;
	
		ground.X = 0;
		ground.Y = -10;
		
		ground.isStatic = true;

	}
}
