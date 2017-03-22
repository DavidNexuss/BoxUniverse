package com.nsoft.boxuniverse.misc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.utils.Json;
import com.nsoft.boxuniverse.main.Game;

public class BaseMaterial {

	public static MaterialsList materials;
	public static HashMap<String, BaseMaterial>worldmaterials= new HashMap<>();
	
	public MaterialDefinition material;
	Material RealMaterial;
	
	Texture t;
	
	public BaseMaterial(String name){
		
		material = materials.materials.get(name);
		t = new Texture(Gdx.files.internal(material.path));
		
		RealMaterial = new Material(TextureAttribute.createDiffuse(t)); //Create GD3 Material
		
		if(material.hasAlpha)RealMaterial.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)); //Set Alpha
		
		worldmaterials.put(name, this); // Add Material
		
		System.out.println("New Material Created");
	}
	
	public Material getMaterial(){
		
		return RealMaterial;
	}
	public static BaseMaterial load(String name){
		
		if(worldmaterials.containsKey(name))
			return worldmaterials.get(name);
		else
			return new BaseMaterial(name);
	}
	
	public static void LoadMaterials(){
		
		materials = Game.MainJson.fromJson(MaterialsList.class, Gdx.files.internal("com/nsoft/boxuniverse/resources/Materials.list"));

	}
	
	public static String getNameFromIndex(int i){
		
		String[] e = new String[getListSize()];
		materials.materials.keySet().toArray(e);
		return e[i];
	}
	
	public static int getListSize(){
		
		return materials.materials.keySet().size();
	}

	public <T> T getProperty(String p,Class<T> Type){
		
		try {
			Field a = MaterialDefinition.class.getDeclaredField(p);
			T value = (T)a.get(material);
			return value;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
	
	}
	
}
	
}

class MaterialDefinition{
	
	String path;
	boolean hasAlpha;
	float density = 1;
	float restitution = 0;
	float friction = 1;
	
}

class MaterialsList{
	
	public HashMap<String, MaterialDefinition> materials;

}




