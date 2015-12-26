package com.cpratt.gameworld;

import com.cpratt.gameobjects.Block;
import com.cpratt.gameobjects.Player;
import com.cpratt.gameobjects.TrapBlock;
import com.cpratt.helpers.Collider;
import com.cpratt.helpers.LevelBuilder;
import com.cpratt.settings.GS;

import java.util.List;

public class GameWorld {

    private Player player;

    private List<Block> blocks;

    private GameState currentState;

    public enum GameState {
        READY, RUNNING, GAMEOVER, WIN
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
//                restart(); // Don't want to do this because we currently wait for player input to continue
                break;
            case WIN:
                //nothing
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
            // WIN condition
        } else if (player.getPosition().x > 600) {
            currentState = GameState.WIN;
        }
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void respawn() {
        player.reset();
        currentState = GameState.RUNNING;
    }
    public void restart() {
        player.reset();
        currentState = GameState.RUNNING;
        for (Block block : blocks) {
            if (block instanceof TrapBlock) {
                ((TrapBlock) block).setTriggered(false);
            }
        }
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isWon() {
        return currentState == GameState.WIN;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
