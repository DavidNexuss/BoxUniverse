package com.nsoft.boxuniverse.world;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Json.Serializable;
import com.nsoft.boxuniverse.main.Game;

/**
 * Map information
 * @author DavidNexus
 *
 */
public class WorldLoader implements Serializable{
	
	public static WorldLoader WorldLoader = new WorldLoader();
	public Blocks BlockList = new Blocks();
	public MetaData MetaData = new MetaData();
	public WorldDefinition WorldDefinition;
	
	public WorldLoader() {}

	@Override
	public void write(Json json) {
		
		json.writeValue("WorldDefinition",WorldDefinition);
		json.writeValue("BlockList",BlockList);
		json.writeValue("MetaData", MetaData);

	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		
		jsonData = jsonData.child;
		WorldDefinition = json.readValue("WorldDefinition", WorldDefinition.class, jsonData);
		jsonData = jsonData.next;
		
		
	}
	
	public static BaseWorld createWorld(WorldDefinition def){
		
		WorldLoader.WorldDefinition = def;
		return new BaseWorld(def);
	}
	
	
	/**
	 * Debug method to save
	 */
	public static void save(){

		Game.MainJson.setElementType(WorldLoader.class, "BlockList", BaseBlock.BlockDefinition.class);
		
		try {
			
			File a = new File("src/com/nsoft/boxuniverse/resources/maps/map1.map");
			FileOutputStream salida = new FileOutputStream(a);
			salida.write(Game.MainJson.prettyPrint(WorldLoader).getBytes());
			salida.flush();
			salida.close();
			
		} catch (Exception e) {
			
		}
	}
	
	public static void load(){
		
		WorldLoader = Game.MainJson.fromJson(WorldLoader.class, Gdx.files.internal("com/nsoft/boxuniverse/resources/Materials.list"));
	}
	
	static class Blocks{
		
		public HashMap<String, BaseBlock.BlockDefinition> BlockList = new HashMap<String,BaseBlock.BlockDefinition>();

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


