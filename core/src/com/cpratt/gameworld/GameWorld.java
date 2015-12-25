package com.cpratt.gameworld;

import com.badlogic.gdx.math.Vector2;
import com.cpratt.gameobjects.Block;
import com.cpratt.gameobjects.Player;
import com.cpratt.helpers.Collider;
import com.cpratt.helpers.LevelBuilder;
import com.cpratt.settings.GS;

import java.util.List;

public class GameWorld {

    private Player player;

    private List<Block> blocks;

    private GameState gameState;

    public enum GameState {
        RUNNING, GAMEOVER
    }

    public GameWorld() {
        player = new Player(GS.PLAYER_START_X, GS.PLAYER_START_Y);
        blocks = LevelBuilder.buildLevel();
        gameState = GameState.RUNNING;
    }

    public void update(float delta) {
        switch (gameState) {
            case RUNNING:
                player.update(delta);
                Collider.handleCollisions(player, blocks, delta);
                break;
            case GAMEOVER:
                restart();
                break;
            default:
                break;
        }

        if (!player.isAlive()) {
            gameState = GameState.GAMEOVER;
        }
    }

    public void restart() {
        gameState = GameState.RUNNING;
        player.setAlive(true);
        player.setPosition(new Vector2(GS.PLAYER_START_X, GS.PLAYER_START_Y));
    }

    public Player getPlayer() {
        return player;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
