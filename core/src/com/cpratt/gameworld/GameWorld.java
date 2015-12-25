package com.cpratt.gameworld;

import com.cpratt.gameobjects.Block;
import com.cpratt.gameobjects.Player;
import com.cpratt.helpers.Collider;
import com.cpratt.helpers.LevelBuilder;
import com.cpratt.settings.GS;

import java.util.List;

public class GameWorld {

    private Player player;

    private List<Block> blocks;

    public GameWorld() {
        player = new Player(GS.PLAYER_START_X, GS.PLAYER_START_Y);
        blocks = LevelBuilder.buildLevel();
    }

    public void update(float delta) {
        player.update(delta, blocks);
        Collider.handleCollisions(player, blocks, delta);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
