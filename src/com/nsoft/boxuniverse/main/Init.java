package com.nsoft.boxuniverse.main;

import com.nsoft.boxuniverse.misc.BaseMaterial;
import com.nsoft.boxuniverse.world.Layer;

public class Init {

	public static void LoadEverything(){
		
		BaseMaterial.LoadMaterials();
		Layer.init();
	}
}
