package com.nsoft.boxuniverse.world;

import java.time.chrono.IsoEra;

import org.lwjgl.system.MathUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.nsoft.boxuniverse.misc.BaseMaterial;

public class BaseBlock extends BaseObject implements Disposable{

	static ModelBuilder builder = new ModelBuilder();;
	
	int Z;
	BlockDefinition b;

	Model model;
	BaseMaterial basematerial;
	PhysicalBlock block;
	ModelInstance instance;
	
	public BaseBlock(BlockDefinition b, int Z) {
		
		this.Z = Z;
		this.b = b;
		basematerial = b.material;
		block = new PhysicalBlock(b);
		model = builder.createBox(b.width, b.height, b.depth,basematerial.getMaterial(), Usage.Normal | Usage.Position | Usage.TextureCoordinates);
		instance = new ModelInstance(model);
		instance.nodes.get(0).parts.get(0).material.set(basematerial.getMaterial());
		setPosition(b.X, b.Y, 0);
		
		
	}
	
	@Override
	public void dispose() {
		model.dispose();
	}

	@Override
	public void draw(ModelBatch mb) {
		instance.transform.setToRotation(Vector3.Z, MathUtils.radiansToDegrees * block.body.getAngle());
		instance.transform.setTranslation(block.getPos(Axis.X), block.getPos(Axis.Y), Z);
		
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
	BaseMaterial material;
	World world;
	boolean isStatic;
}

class PhysicalBlock{
	
	Body body;
	
	public PhysicalBlock(BlockDefinition b) {
		
		BodyDef def = new BodyDef();
		def.type = b.isStatic ? BodyType.StaticBody: BodyType.DynamicBody;
		def.position.set(b.X, b.Y);
		
		body = b.world.createBody(def);
		
		FixtureDef fixdef = new FixtureDef();
		PolygonShape s = new PolygonShape();
		s.setAsBox(b.width, b.height);
		
		fixdef.friction = b.material.getProperty("friction", Float.class);
		fixdef.density = b.material.getProperty("density",Float.class);
		fixdef.restitution = b.material.getProperty("restitution", Float.class);
		fixdef.shape = s;
		
		body.createFixture(fixdef);
		
	}
	
	public float getPos(Axis a){
		
		if(a == Axis.X)
			
			return body.getPosition().x/2;
		else
			
			return body.getPosition().y/2;
		
	}
	
}

enum Axis{
	
	X,Y
}