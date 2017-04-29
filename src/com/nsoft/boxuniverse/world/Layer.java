package com.nsoft.boxuniverse.world;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.nsoft.boxuniverse.misc.BaseMaterial;
import com.nsoft.boxuniverse.world.BaseBlock.BlockDefinition;

public class Layer {
	
	int Z;
	static Vector2 gravity = new Vector2(0, -9.8f);
	BaseBlock.BlockDefinition ground;
	public World mundo;
	Box2DDebugRenderer renderer;
	private ArrayList<BaseObject> objects;
	
	
	public Layer(int z,boolean needGround) {
		
		renderer = new Box2DDebugRenderer();
		Z = z;
		mundo = new World(gravity , true);
		objects = new ArrayList<>();
		
		if(needGround){
		
			ground = init();
			addNewBlock(ground);
		}
		
	}
	
	public void addObject(BaseObject B){
		
		objects.add(B);
	}
	
	public void addNewBlock(BaseBlock.BlockDefinition B){
		
		objects.add(new BaseBlock(B, Z, BaseBlock.RANDOM));
	}
	
	public void addLoadedBlock(BlockDefinition block,String name) {
		
		objects.add(new BaseBlock(block, Z, name));

	}
	public void render(ModelBatch mb){
		
		mundo.step(Gdx.graphics.getDeltaTime(), 6, 2);
		for (BaseObject baseObject : objects) {
			
			baseObject.draw(mb);
		}
		
		renderer.render(mundo, mb.getCamera().projection);
	}
	
	public BlockDefinition init(){
		
		ground = new BaseBlock.BlockDefinition();
		ground.material = BaseMaterial.load("Brick");
		ground.depth  = 1;
		ground.width = 20;
		ground.height = 1;
		ground.world = mundo;
		ground.X = 0;
		ground.Y = -10;
		ground.Z = Z;
		ground.isStatic = true;
		
		return ground;

	}

	public void foreach(Act act){
		
		for (BaseObject baseObject : objects) {
			
			act.act(baseObject);
		}
		
	}
}
