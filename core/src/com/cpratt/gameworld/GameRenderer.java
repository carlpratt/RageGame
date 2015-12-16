package com.cpratt.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.cpratt.gameobjects.Block;
import com.cpratt.gameobjects.Player;
import com.cpratt.helpers.AssetLoader;
import com.cpratt.settings.GS;

import java.io.BufferedReader;
import java.io.FileReader;

public class GameRenderer {

    private GameWorld world;

    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    // Game Objects
    private Player player;

    // Game Assets
    private TextureRegion bg, grass;
    private Animation birdAnimation;
    private TextureRegion birdMid, birdDown, birdUp;
    private TextureRegion skullUp, skullDown, bar, bricks, playerAsset;

    BufferedReader br;
    String everything;

    public GameRenderer(GameWorld world) {
        this.world = world;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, GS.SCREEN_WIDTH, GS.SCREEN_HEIGHT);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();

        try {
            br = new BufferedReader(new FileReader("/Users/cpratt/dev/private/RageGame/core/src/com/cpratt/levels/01.txt"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
            Gdx.app.log("Everything", everything);
        } catch (Exception e) {

        }
    }

    public void render(float runTime) {

        // We will move these outside of the loop for performance later.
//        Bird bird = myWorld.getBird();

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, GS.SCREEN_WIDTH, GS.SCREEN_HEIGHT);

        // End ShapeRenderer
        shapeRenderer.end();


        batcher.begin();
        batcher.disableBlending();

        for (Block block : world.getBlocks()) {
            batcher.draw(bricks, block.getPosition().x, block.getPosition().y, block.getWidth(), block.getHeight());
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//            shapeRenderer.setColor(Color.RED);
//            shapeRenderer.rect(block.getBounds().getX(), block.getBounds().getY(), block.getBounds().getWidth(), block.getBounds().getHeight());
//            shapeRenderer.end();
        }

        batcher.enableBlending();
        batcher.end();

        batcher.begin();
        batcher.draw(playerAsset, player.getX(), player.getY(), 15, 20);
        batcher.end();

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.GREEN);
//        shapeRenderer.rect(player.getBounds().getX(), player.getBounds().getY(), player.getBounds().getWidth(), player.getBounds().getHeight());
//        shapeRenderer.end();
    }

    private void initGameObjects() {
        player = world.getPlayer();
    }

    private void initAssets() {
//        bg = AssetLoader.bg;
//        grass = AssetLoader.grass;
//        birdAnimation = AssetLoader.birdAnimation;
//        birdMid = AssetLoader.bird;
//        birdDown = AssetLoader.birdDown;
//        birdUp = AssetLoader.birdUp;
//        skullUp = AssetLoader.skullUp;
//        skullDown = AssetLoader.skullDown;
//        bar = AssetLoader.bar;
        bricks = AssetLoader.bricks;
        playerAsset = AssetLoader.player;
    }
}