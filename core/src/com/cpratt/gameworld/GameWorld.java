package com.cpratt.gameworld;

import com.badlogic.gdx.Gdx;
import com.cpratt.gameobjects.Block;
import com.cpratt.gameobjects.Player;
import com.cpratt.settings.GS;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private Player player;

    private List<Block> blocks = new ArrayList<Block>();

    public GameWorld() {
        player = new Player(100, 100);
        blocks = createBlocks(readLevel());
    }

    public void update(float delta) {
        player.update(delta, blocks);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    private String readLevel() {
        BufferedReader br;
        String everything = null;

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
        return everything;
    }

    private List<Block> createBlocks(String everything) {
        int x = 0;
        int y = 0;
        for (Character c : everything.toCharArray()) {
//            Gdx.app.log("Char", c.toString());
            if (c.equals('b')) {
                blocks.add(new Block(x, y));
                x += 20;
            } else if (c.equals('\n')) {
                y += 20;
                x = 0;
            } else {
                x += 20;
            }
        }
        return blocks;
    }
}
