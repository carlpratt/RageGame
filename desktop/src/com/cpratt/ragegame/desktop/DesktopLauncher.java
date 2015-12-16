package com.cpratt.ragegame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.cpratt.ragegame.RageGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Rage Game";
        config.width = 960;
        config.height = 640;
        new LwjglApplication(new RageGame(), config);
	}
}
