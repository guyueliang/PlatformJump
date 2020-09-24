package com.platformjump.game.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.platformjump.game.platformjump;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Graphics.DisplayMode[] modes = LwjglApplicationConfiguration.getDisplayModes();
		Graphics.DisplayMode desktopMode = LwjglApplicationConfiguration.getDesktopDisplayMode();
		config.width = 1280;
		config.height = 720;
		//config.x = 0;
		//config.y = 0;
		config.title = " platformjump";
		//config.setFromDisplayMode(desktopMode);

		new LwjglApplication(new platformjump(), config);
	}
}
