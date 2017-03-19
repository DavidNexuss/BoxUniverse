package com.nsoft.boxuniverse.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g3d.ModelBatch;

public class Layer {
	
	int Z;
	private ArrayList<BaseObject> objects;
	
	public Layer(int z) {
		
		Z = z;
		objects = new ArrayList<>();
	}
	
	public void addObject(BaseObject B){
		
		objects.add(B);
	}
	
	public void addNewBlock(BlockDefinition B){
		
		objects.add(new BaseBlock(B, Z));
	}
	public void render(ModelBatch mb){
		
		for (BaseObject baseObject : objects) {
			
			baseObject.draw(mb);
		}
	}
}
