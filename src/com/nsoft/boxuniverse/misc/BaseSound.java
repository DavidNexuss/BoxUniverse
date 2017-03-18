package com.nsoft.boxuniverse.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class BaseSound extends BaseItem {
	
	
	static HashMap<String, SoundDefinition> sounds = new HashMap<>();
	static ArrayList<SoundDefinition> soundslist = new ArrayList<>();
	
	Sound audio;
	Texture tex;
	SoundDefinition def;
	
	float Volume = 1f;
	
	public BaseSound(String SoundName) {
		
		if(sounds.containsKey(SoundName)){
			
			def = sounds.get(SoundName);
			audio = Gdx.audio.newSound(Gdx.files.internal(def.SoundPath));
			tex = new Texture(Gdx.files.internal(def.TexturePath));
		}
	}
	public void play(){
		
		long id =audio.play();
		audio.setVolume(id, Volume);
	}

	public void stop(){
		
		audio.stop();
	}
	public void dispose(){
		
		audio.dispose();
		tex.dispose();
	}

	/**
	 * Load all the sounds definitions from Sounds.list
	 */
	public static void loadSounds(){
		

		try {
			
			Json a = new Json();
			soundslist = a.fromJson(ArrayList.class, Gdx.files.internal("com/nsoft/boxuniverse/resources/Sounds.list"));
			
			for (SoundDefinition soundDefinition : soundslist) 
				
				sounds.put(soundDefinition.SoundName, soundDefinition);
			System.out.println(a.prettyPrint(soundslist));
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}
	
	public static void debug(){
	
	}

}
class SoundDefinition implements Serializable{

	String SoundPath;
	String TexturePath;
	String SoundName;
	
	public SoundDefinition() {
		
	}

	public SoundDefinition(	String SoundPath, String TexturePath, String SoundName){
		
		this.SoundPath = SoundPath;
		this.SoundName = SoundName;
		this.TexturePath = TexturePath;
	}
	
	@Override
	public void write(Json json) {
		
		json.writeValue("SoundPath", SoundPath);
		json.writeValue("TexturePath", TexturePath);
		json.writeValue(SoundName, "SoundName");
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		
		SoundPath = jsonData.child.next.asString();
		TexturePath = jsonData.child.next.next.asString();
		SoundName = jsonData.child.next.next.next.asString();
	}
	
}
