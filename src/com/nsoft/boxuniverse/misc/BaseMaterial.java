/*
 * The MIT License
 *
 * Copyright 2017 DavidNexuss.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.nsoft.boxuniverse.misc;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.nsoft.boxuniverse.main.Game;


/**
 * BaseMaterial Class
 * @author DavidNexus
 *
 */
public class BaseMaterial {

	public static MaterialsList materials; //Pre-Cached Materials List
	public static HashMap<String, BaseMaterial>worldmaterials= new HashMap<>(); //Cached Materials
	
	public MaterialDefinition material;
	Material RealMaterial; //G3D RealMaterial
	public Texture t;
	public String materialName;
	
	public BaseMaterial(String name){
		
		materialName = name;
		material = materials.materials.get(name);
		t = new Texture(Gdx.files.internal(material.path));
		
		RealMaterial = new Material(TextureAttribute.createDiffuse(t)); //Create GD3 Material
		
		if(material.hasAlpha)RealMaterial.set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)); //Set Alpha
		worldmaterials.put(name, this); // Add Material
		
		System.out.println("New Material Created");
	}
	
	/**
	 * It returns the real G3D material.
	 * @return The real G3D material
	 */
	public Material getMaterial(){ return  RealMaterial; }
	

	/**
	 * Precache all the materials in the game
	 */
	public static void LoadMaterials(){
		
		materials = Game.MainJson.fromJson(MaterialsList.class, Gdx.files.internal("com/nsoft/boxuniverse/resources/Materials.list"));

	}
	
	/**
	 * Returns a BaseMaterial object, if its has already been loaded, it returns it from the list
	 * @param Name of the material
	 * @return The needed material
	 */
	public static BaseMaterial load(String name){
		
		if(worldmaterials.containsKey(name))
			return worldmaterials.get(name);
		else
			return new BaseMaterial(name);
	}
	
	
	/**
	 * Return the name of a material by the given index
	 * @param i index
	 * @return Name
	 */
	public static String getNameFromIndex(int i){
		
		String[] e = new String[getListSize()];
		materials.materials.keySet().toArray(e);
		return e[i];
	}
	
	/** 
	 * @return List size
	 */
	public static int getListSize(){
		
		return materials.materials.keySet().size();
	}

}



class MaterialsList{
	
	public HashMap<String, MaterialDefinition> materials;

}




