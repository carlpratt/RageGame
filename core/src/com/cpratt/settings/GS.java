package com.cpratt.settings;

import com.badlogic.gdx.Gdx;

public class GS {
    public static int SCREEN_WIDTH = Gdx.graphics.getWidth() / 2; // Scaled for ortho cam
    public static int SCREEN_HEIGHT = Gdx.graphics.getHeight() / 2; // Scaled for ortho cam

    // Movement
    public static int ZERO = 0; // Dumb, but makes it slightly easier to track down where the value 0 is used.

    public static int PLAYER_LATERAL_VELOCITY = 75;
    public static int PLAYER_VERTICAL_VELOCITY = -200;

    public static int GRAVITY_ACCELERATION = 250;

    // Assets
    public static int PLAYER_WIDTH = 15;
    public static int PLAYER_HEIGHT = 20;

    public static int BLOCK_HEIGHT = 20;
    public static int BLOCK_WIDTH = 20;

    public static int PLAYER_START_X = 60;
    public static int PLAYER_START_Y = 220;

    // Camera
    public static int CAMERA_OFFSET_X = 100;

}
