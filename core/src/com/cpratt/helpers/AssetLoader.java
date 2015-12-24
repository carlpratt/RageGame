package com.cpratt.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture brickTexture, playerTextureLeft, playerTextureRight;
    public static TextureRegion bg, grass;

    public static Animation birdAnimation;
    public static TextureRegion bird, birdDown, birdUp;

    public static TextureRegion skullUp, skullDown, bar, bricks, playerLeft, playerRight;

    public static void load() {

        brickTexture = new Texture(Gdx.files.internal("data/stone_wall02.png"));
        brickTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        bricks = new TextureRegion(brickTexture, 0, 0, 1024, 1024);
        bricks.flip(false, true);

        playerTextureRight = new Texture(Gdx.files.internal("data/marioRight.png"));
        playerTextureRight.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        playerRight = new TextureRegion(playerTextureRight, 0, 0, 480, 640);
        playerRight.flip(false, true);

        playerTextureLeft = new Texture(Gdx.files.internal("data/marioLeft.png"));
        playerTextureLeft.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        playerLeft = new TextureRegion(playerTextureLeft, 0, 0, 480, 640);
        playerLeft.flip(false, true);


//        bricks = new TextureRegion(texture, 0, 0, 1024, 1024);
//        bricks.flip(false, true);
//
//        bg = new TextureRegion(texture, 0, 0, 136, 43);
//        bg.flip(false, true);
//
//        grass = new TextureRegion(texture, 0, 43, 143, 11);
//        grass.flip(false, true);
//
//        birdDown = new TextureRegion(texture, 136, 0, 17, 12);
//        birdDown.flip(false, true);
//
//        bird = new TextureRegion(texture, 153, 0, 17, 12);
//        bird.flip(false, true);
//
//        birdUp = new TextureRegion(texture, 170, 0, 17, 12);
//        birdUp.flip(false, true);
//
//        TextureRegion[] birds = { birdDown, bird, birdUp };
//        birdAnimation = new Animation(0.06f, birds);
//        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
//
//        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
//        // Create by flipping existing skullUp
//        skullDown = new TextureRegion(skullUp);
//        skullDown.flip(false, true);
//
//        bar = new TextureRegion(texture, 136, 16, 22, 3);
//        bar.flip(false, true);

    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        brickTexture.dispose();
        playerTextureRight.dispose();
        playerTextureLeft.dispose();
    }

}
