package com.nsoft.boxuniverse.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.PerspectiveCamera;

public class Controls {

	/**
	 * Aplica translaciones a la camara
	 * @param pc
	 * @param delta
	 */
	public static void CamTest(PerspectiveCamera pc,float delta){
		
		//No se usa else if para asi poder hacer movimientos en diagonal
		
		delta *= 2;
		if(Gdx.input.isKeyPressed(Keys.W)){
			pc.translate(0, delta, 0);
			
		}if(Gdx.input.isKeyPressed(Keys.S)){
			pc.translate(0, -delta, 0);
		}
		if(Gdx.input.isKeyPressed(Keys.A)){
			pc.translate(-delta, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Keys.D)){
			pc.translate(delta, 0, 0);
		}
			
			
		
		pc.update();
	}
}
