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
		
		delta *= pc.position.z/2;
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
