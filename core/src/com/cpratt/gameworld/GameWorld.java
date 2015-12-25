package com.cpratt.gameworld;

import com.badlogic.gdx.math.Vector2;
import com.cpratt.gameobjects.Block;
import com.cpratt.gameobjects.Player;
import com.cpratt.helpers.Collider;
import com.cpratt.helpers.LevelBuilder;
import com.cpratt.settings.GS;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class GameWorld {

    private Player player;

    private List<Block> blocks;

    private GameState currentState;

    public enum GameState {
        READY, RUNNING, GAMEOVER
    }

    public GameWorld() {
        player = new Player(GS.PLAYER_START_X, GS.PLAYER_START_Y);
        blocks = LevelBuilder.buildLevel();
        currentState = GameState.RUNNING;
    }

    public void update(float delta) {
        switch (currentState) {
            case READY:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case GAMEOVER:
//                restart();
                break;
            default:
                break;
        }
    }

    private void updateReady(float delta) {

    }

    private void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        player.update(delta);
        Collider.handleCollisions(player, blocks, delta);

        if (!player.isAlive()) {
            currentState = GameState.GAMEOVER;
        }
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void restart() {
        try {
//            Thread.sleep(1000);
        } catch (Exception e) {
            // nothing
        }
        player.reset();
        blocks = LevelBuilder.buildLevel();
//        blocks = LevelBuilder.buildLevel();
        currentState = GameState.RUNNING;

    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
