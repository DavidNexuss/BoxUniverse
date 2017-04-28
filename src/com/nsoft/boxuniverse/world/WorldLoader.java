package com.nsoft.boxuniverse.world;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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
	
	public HashMap<String, BaseBlock.BlockDefinition> BlockList = new HashMap<String,BaseBlock.BlockDefinition>();
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
		System.out.println(Game.MainJson.prettyPrint(WorldLoader));
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
			
		}
		
		
	}
}


