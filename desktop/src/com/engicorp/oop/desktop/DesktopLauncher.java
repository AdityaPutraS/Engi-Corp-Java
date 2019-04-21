package com.engicorp.oop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.engicorp.oop.EngiCorp;
import com.engicorp.oop.product.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
//		BeefRolade br1 = new BeefRolade();
//		System.out.println(br1.getName());
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new EngiCorp(), config);
	}
}
