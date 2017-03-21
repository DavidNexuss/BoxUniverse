package com.nsoft.boxuniverse.misc;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class BaseMaterial {

	static MaterialsList materials;
	
	MaterialDefinition material;
	Texture t;
	
	public BaseMaterial(String name) {
		
		material = materials.materials.get(name);
		t = new Texture(material.path);
	}
	
	public static void LoadMaterials(){
		
		Json j = new Json();
		materials = j.fromJson(MaterialsList.class, Gdx.files.internal("com/nsoft/boxuniverse/resources/Materials.list"));

	}

}

class MaterialDefinition{
	
	String path;
	
}

class MaterialsList{
	
	HashMap<String, MaterialDefinition> materials;

}


