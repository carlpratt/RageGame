package com.cpratt.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.cpratt.gameobjects.Block;
import com.cpratt.gameobjects.Player;
import com.cpratt.gameobjects.TrapBlock;
import com.cpratt.helpers.AssetLoader;
import com.cpratt.settings.GS;

import java.util.ArrayList;
import java.util.List;

public class GameRenderer {

    private GameWorld world;

    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    // Game Objects
    private Player player;
    private List<Block> blocks;
    private List<TrapBlock> triggeredTrapBlocksToRender = new ArrayList<TrapBlock>();

    // Game Assets
    private TextureRegion bricks, playerAsset, playerAssetLeft, playerAssetRight, lastPlayerAsset;

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
    }

    public void render(float runTime) {
        cam.position.x = player.getX() + GS.CAMERA_OFFSET_X;
        cam.update();
        batcher.setProjectionMatrix(cam.combined);

        // Set black background color
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        // uuuuuuggggggghhhhhhh...x coord hack to make sure background stays in correct place. shaperenderer is projected on to player
        shapeRenderer.rect(cam.position.x - (GS.SCREEN_WIDTH / 2), 0, GS.SCREEN_WIDTH, GS.SCREEN_HEIGHT);
        shapeRenderer.end();

        // Time to render the blocks
        for (Block block : blocks) {
            batcher.begin();
            batcher.disableBlending();
                batcher.draw(bricks, block.getPosition().x, block.getPosition().y, block.getWidth(), block.getHeight());
            batcher.enableBlending();
            batcher.end();

            // Make sure to remove reset blocks. If this list gets too big the framerate nosedives.
            if (block instanceof TrapBlock) {
                if (((TrapBlock) block).isTriggered() && !triggeredTrapBlocksToRender.contains(block)) {
                    triggeredTrapBlocksToRender.add((TrapBlock) block);
                } else if (!((TrapBlock) block).isTriggered() && triggeredTrapBlocksToRender.contains(block)) {
                    triggeredTrapBlocksToRender.remove(block);
                }
            }
        }

        if (player.getVelocity().x > 0) {
            playerAsset = playerAssetRight;
            lastPlayerAsset = playerAssetRight;
        } else if (player.getVelocity().x < 0) {
            playerAsset = playerAssetLeft;
            lastPlayerAsset = playerAssetLeft;
        } else {
            playerAsset = lastPlayerAsset != null ? lastPlayerAsset : playerAssetRight;
        }

        batcher.begin();
        batcher.draw(playerAsset, player.getX(), player.getY(), 15, 20);
        batcher.end();

        // Do this at the end so nothing renders over the spikes
        for (TrapBlock trapBlock : triggeredTrapBlocksToRender) {
            renderSpikesOnTrapBlock(trapBlock);
        }

        // Render player bounds
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.GREEN);
//        shapeRenderer.rect(player.getBounds().getX(), player.getBounds().getY(), player.getBounds().getWidth(), player.getBounds().getHeight());
//        shapeRenderer.end();

        if (world.isGameOver()) {
            batcher.begin();
            BitmapFont font = new BitmapFont(true);
            font.setScale(2);
            font.drawMultiLine(batcher, "     GAME OVER\n (Click to continue)", player.getX(), 100);
            batcher.end();
        } else if (world.isWon()) {
            batcher.begin();
            BitmapFont font = new BitmapFont(true);
            font.setScale(2);
            font.drawMultiLine(batcher, "     YOU WIN\n (Click to restart)", player.getX(), 100);
            batcher.end();
        }
    }

    private void initGameObjects() {
        player = world.getPlayer();
        blocks = world.getBlocks();
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
        playerAssetLeft = AssetLoader.playerLeft;
        playerAssetRight = AssetLoader.playerRight;
    }

    private void renderSpikesOnTrapBlock(Block block) {
        float x = block.getPosition().x;
        float y = block.getPosition().y;

        while (x < block.getPosition().x + block.getWidth()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.LIGHT_GRAY);
            shapeRenderer.triangle(x, y, x + 4, y, x + 2, y - 4);
            shapeRenderer.end();
            x += 4;
        }

        x = block.getPosition().x;
        y = block.getPosition().y;

        while (y < block.getPosition().y + block.getHeight()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.LIGHT_GRAY);
            shapeRenderer.triangle(x, y, x, y + 4, x - 4, y + 2);
            shapeRenderer.end();
            y += 4;
        }

        x = block.getPosition().x + block.getWidth();
        y = block.getPosition().y;

        while (y < block.getPosition().y + block.getHeight()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.LIGHT_GRAY);
            shapeRenderer.triangle(x, y, x, y + 4, x + 4, y + 2);
            shapeRenderer.end();
            y += 4;
        }

        x = block.getPosition().x;
        y = block.getPosition().y + block.getHeight();

        while (x < block.getPosition().x + block.getWidth()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.LIGHT_GRAY);
            shapeRenderer.triangle(x, y, x + 4, y, x + 2, y + 4);
            shapeRenderer.end();
            x += 4;
        }
    }
}
