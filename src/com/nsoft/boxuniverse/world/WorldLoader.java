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

package com.nsoft.boxuniverse.world;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializable;
import com.nsoft.boxuniverse.main.Game;
import com.nsoft.boxuniverse.misc.BaseMaterial;
import com.nsoft.boxuniverse.world.BaseBlock.BlockDefinition;

/**
 * Map information
 * @author DavidNexus
 *
 */
public class WorldLoader implements Serializable{
	
	public static WorldLoader WorldLoader = new WorldLoader();
	public Blocks BlockInformation = new Blocks();
	public MetaData MetaData = new MetaData();
	public WorldDefinition WorldDefinition;
	
	public WorldLoader() {}

	@Override
	public void write(Json json) {
		
		json.writeValue("WorldDefinition",WorldDefinition);
		json.writeValue("BlockInformation",BlockInformation);
		json.writeValue("MetaData", MetaData);

	}

	@Override
	public void read(Json json, JsonValue jsonData) {

		WorldDefinition = json.readValue("WorldDefinition", WorldDefinition.class, jsonData);

		BlockInformation = json.readValue("BlockInformation",Blocks.class, jsonData);

		MetaData = json.readValue("MetaData",MetaData.class,jsonData);
		
	}
	
	/**
	 * Debug method to save
	 */
	public static void save(){

		Game.MainJson.setElementType(WorldLoader.class, "BlockInformation", BaseBlock.BlockDefinition.class);
		System.out.println(Game.MainJson.prettyPrint(WorldLoader));
		try {
			
			File a = new File("src/com/nsoft/boxuniverse/resources/maps/map1.map");
			FileOutputStream salida = new FileOutputStream(a);
			salida.write(Game.MainJson.prettyPrint(WorldLoader).getBytes());
			salida.flush();
			salida.close();
			
		} catch (Exception e) {
			
		}
	}
	
	public static BaseWorld loadNewMap(WorldDefinition def){
		
		WorldLoader.WorldDefinition = def;
		return new BaseWorld(def,true);
	}
	public static BaseWorld loadMap(){
		
		BaseWorld b = new BaseWorld(WorldLoader.WorldDefinition, false);
		
		for(Entry<String, BlockDefinition> entry : WorldLoader.BlockInformation.BlockList.entrySet()) {
			
			b.handleBlockCreation(entry.getValue(),entry.getKey());
		}
		
		BaseBlock.blockCount = WorldLoader.BlockInformation.BlockList.entrySet().size();
		
		
		return b;
	}
	public static void load(String mapName){

		WorldLoader = Game.MainJson.fromJson(WorldLoader.class, Gdx.files.internal(mapName));
	}
	
	static class Blocks{
		
		public HashMap<String, BaseBlock.BlockDefinition> BlockList = new HashMap<String,BaseBlock.BlockDefinition>();
		
		public Blocks() {}


	}
	static class MetaData implements Serializable{
		
		String author = System.getProperty("user.name");
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
		
		public MetaData() {}
		
		@Override
		public void write(Json json) {
			
			json.writeValue("Author",author);
			json.writeValue("Date Of Creation", date);
		}
		@Override
		public void read(Json json, JsonValue jsonData) {
			
			author = jsonData.child.asString();
			date = jsonData.child.next.asString();
		}
		
		
	}
}


