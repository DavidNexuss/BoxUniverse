package com.nsoft.boxuniverse.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;

public class BaseBlock extends BaseObject implements Disposable{

	static ModelBuilder builder = new ModelBuilder();;
	
	int Z;
	BlockDefinition b;
	
	Model model;
	ModelInstance instance;
	public BaseBlock(BlockDefinition b, int Z) {
		
		this.Z = Z;
		this.b = b;
		model = builder.createBox(b.width, b.height, b.depth, new Material(ColorAttribute.createDiffuse(b.color)), Usage.Normal | Usage.Position);
		instance = new ModelInstance(model);
		setPosition(b.X, b.Y, 0);
	}
	
	@Override
	public void dispose() {
		model.dispose();
	}

	@Override
	public void draw(ModelBatch mb) {
		
		mb.render(instance,BaseWorld.e);

	}

	@Override
	public Vector3 getPosition() {
		return instance.transform.getTranslation(Vector3.Zero);
	}

	@Override
	public void setPosition(int x, int y, int z) {
		
		instance.transform.setTranslation(x, y, Z);
	}
	
	
}

class BlockDefinition{
	
	int X;
	int Y;
	int width;
	int height;
	int depth;
	Color color; //Just for debugging
}
