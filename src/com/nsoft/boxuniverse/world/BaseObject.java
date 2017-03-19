package com.nsoft.boxuniverse.world;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class BaseObject {

	public abstract void draw(ModelBatch mb);
	public abstract Vector3 getPosition();
	public abstract void setPosition(int x,int y, int z);
}
