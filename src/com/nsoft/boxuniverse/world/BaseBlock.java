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
import com.badlogic.gdx.math.Matrix3;
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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.nsoft.boxuniverse.misc.BaseMaterial;

public class BaseBlock extends BaseObject implements Disposable{

	static ModelBuilder builder = new ModelBuilder();;
	static final String RANDOM = "RN";
	static int blockCount = 0;
	int Z;
	String name;
	BlockDefinition b;

	Model model;
	BaseMaterial basematerial;
	PhysicalBlock block;
	ModelInstance instance;
	
	public BaseBlock(BlockDefinition b, int Z,String name) {
		
		blockCount++;
		this.Z = Z;
		this.b = b;
		b.Z = Z;
		basematerial = b.material;
		block = new PhysicalBlock(b);
		model = builder.createBox(b.width, b.height, b.depth,basematerial.getMaterial(), Usage.Normal | Usage.Position | Usage.TextureCoordinates);
		instance = new ModelInstance(model);
		instance.nodes.get(0).parts.get(0).material.set(basematerial.getMaterial());
		setPosition(b.X, b.Y, 0);
		
		if(name.equals(RANDOM)){
			
			name = String.valueOf(blockCount);
		}
		
		this.name = name;
		
		if(!WorldLoader.WorldLoader.BlockInformation.BlockList.containsKey(name))WorldLoader.WorldLoader.BlockInformation.BlockList.put(name, b);
	}
	
	@Override
	public void dispose() {
		model.dispose();
	}

	@Override
	public void draw(ModelBatch mb) {
		
		if(block.body.isAwake()){
			instance.transform.setToRotation(Vector3.Z, MathUtils.radiansToDegrees * block.body.getAngle());
			instance.transform.setTranslation(block.getPos(Axis.X), block.getPos(Axis.Y), Z);
		
		}
		
		mb.render(instance,BaseWorld.e);
		

	}

	@Override
	public Vector3 getPosition() {
		return instance.transform.getTranslation(Vector3.Zero);
	}

	@Override
	public void setPosition(float x, float y, int z) {
		
		instance.transform.setTranslation(x, y, Z);
	}
	
	@Override
	public void saveStatus(){
		
		b.X = block.getPos(Axis.X)*2;
		b.Y = block.getPos(Axis.Y)*2;
		b.Angle = block.getAngle()*MathUtils.radiansToDegrees;
	}
	static class BlockDefinition implements Serializable{
		
		float X;
		float Y;
		float Z;
		float Angle = 0;
		int width = 1;
		int height = 1; 
		int depth = 1; 
		BaseMaterial material;
		World world;
		boolean isStatic;
		
		public BlockDefinition() {}
		
		@Override
		public void write(Json json) {

			json.writeValue("Position",new float[]{X,Y,Z});
			json.writeValue("Size",new int[]{width,height,depth});
			if(Angle != 0) json.writeValue("Angle", Angle);
			json.writeValue("MaterialName",material.materialName);
			json.writeValue("IsStatic",isStatic);
		}
		@Override
		public void read(Json json, JsonValue jsonData) {
			

			jsonData = jsonData.child;
			
			X = jsonData.asFloatArray()[0];
			Y = jsonData.asFloatArray()[1];
			Z = jsonData.asFloatArray()[2];
			
			jsonData = jsonData.next;

			width = jsonData.asIntArray()[0];
			height = jsonData.asIntArray()[1];
			depth = jsonData.asIntArray()[2];

			jsonData = jsonData.next;
			
			if(jsonData.name.equals("Angle")){
				
				Angle = jsonData.asFloat();
				jsonData = jsonData.next;
			}
			material = BaseMaterial.load(jsonData.asString());
			
			jsonData =  jsonData.next;
			
			isStatic = jsonData.asBoolean();

		}
		
		public BlockDefinition clone(){
			
			try {
				
				return (BlockDefinition)super.clone();
			
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

	}
	
}



class PhysicalBlock{
	
	Body body;
	
	public PhysicalBlock(BaseBlock.BlockDefinition b) {
		
		BodyDef def = new BodyDef();
		def.type = b.isStatic ? BodyType.StaticBody: BodyType.DynamicBody;
		def.position.set(b.X, b.Y);
		def.angle = b.Angle*MathUtils.degreesToRadians;
		body = b.world.createBody(def);
		
		FixtureDef fixdef = new FixtureDef();
		PolygonShape s = new PolygonShape();
		s.setAsBox(b.width, b.height);
		
		fixdef.friction = b.material.material.friction;
		fixdef.density = b.material.material.density;
		fixdef.restitution = b.material.material.restitution;
		
		fixdef.shape = s;
		
		body.createFixture(fixdef);
		
		
		
	}
	
	public float getPos(Axis a){
		
		if(a == Axis.X)
			
			return body.getPosition().x/2;
		else
			
			return body.getPosition().y/2;
		
	}
	
	public float getAngle(){
		
		return body.getAngle();
	}
	
}

enum Axis{
	
	X,Y
}
