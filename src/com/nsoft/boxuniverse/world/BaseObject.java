package com.nsoft.boxuniverse.world;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.nsoft.boxuniverse.main.Game;

public abstract class BaseObject {

	public abstract void draw(ModelBatch mb);
	public abstract Vector3 getPosition();
	public abstract void setPosition(int x,int y, int z);
	
}

