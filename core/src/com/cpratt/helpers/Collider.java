package com.cpratt.helpers;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.cpratt.gameobjects.Block;
import com.cpratt.gameobjects.Player;
import com.cpratt.gameobjects.TrapBlock;
import com.cpratt.settings.GS;

import java.util.List;

public class Collider {

    public static void handleCollisions(Player player, List<Block> blocks, float delta) {
        // For every asset, determine if there was a collision, and handle player vectors appropriately.
        // Example: Player collides with block top, player's y velocity should be 0 and y pos should match block y + player height
        for (Block block : blocks) {
            if (collides(player.getPosition(), player.getBounds(), block)) {
//                System.out.println("COLLIDES");

                if (block instanceof TrapBlock) {
                    player.setAlive(false);
                }

                // Calculate overlaps between player and colliding block. Used to determine player final destination relative to block
                float xOverlapLeft = Math.abs(player.getPosition().x + GS.PLAYER_WIDTH - block.getPosition().x);
                float xOverlapRight = Math.abs(block.getPosition().x + GS.BLOCK_WIDTH - player.getPosition().x);

                float yOverlapTop = Math.abs(player.getPosition().y + GS.PLAYER_HEIGHT - block.getPosition().y);
                float yOverlapBottom = Math.abs(block.getPosition().y + GS.BLOCK_HEIGHT - player.getPosition().y);

                float xOverlap = xOverlapLeft < xOverlapRight ? xOverlapLeft : xOverlapRight;
                float yOverlap = yOverlapTop < yOverlapBottom ? yOverlapTop : yOverlapBottom;

//                System.out.println("yOverlap: " + yOverlap);
//                System.out.println("xOverlap: " + xOverlap);

                // Slightly favor y since block top collisions are most common
                if (yOverlap < xOverlap + 1) {
                    if (yOverlapTop < yOverlapBottom) {
                        player.getPosition().y = block.getPosition().y - GS.PLAYER_HEIGHT;
                    } else {
                        player.getPosition().y = block.getPosition().y + GS.BLOCK_HEIGHT;
                    }
                    player.getVelocity().y = 0;
                } else {
                    if (xOverlapLeft < xOverlapRight) {
                        player.getPosition().x = block.getPosition().x - GS.PLAYER_WIDTH;
                    } else {
                        player.getPosition().x = block.getPosition().x + GS.BLOCK_WIDTH;
                    }
                    player.stopMoving();
                }
                break;
            }
        }

        // Allows player to continue moving left / right after jumping over an obstacle
        Vector2 tmpPos = new Vector2();
        float tmpVelX = player.isRightKeyPressed() ? GS.PLAYER_LATERAL_VELOCITY : player.isLeftKeyPressed() ? -GS.PLAYER_LATERAL_VELOCITY : 0;
        float tmpVelY = player.getVelocity().y;
        tmpVelX *= delta;
        tmpVelY *= delta;
        tmpPos.x = player.getPosition().x + tmpVelX;
        tmpPos.y = player.getPosition().y + tmpVelY;
        Rectangle tmpBounds = new Rectangle(tmpPos.x, tmpPos.y, GS.PLAYER_WIDTH, GS.PLAYER_HEIGHT);

        boolean collision = false;

        for (Block b : blocks) {
            if (collides(tmpPos, tmpBounds, b))
                collision = true;
        }

        if (!collision) {
//            System.out.println("MOVING");
            if (player.isRightKeyPressed()) {
                player.moveRight();
            }
            if (player.isLeftKeyPressed()) {
                player.moveLeft();
            }
        }

        if (player.getPosition().y > GS.SCREEN_HEIGHT) {
            System.out.println("DEAD");
            System.out.println(player.getY());
            player.setAlive(false);
        }
    }

    /**
     * Determine if collision has happened. Only calculate collision if block is within range of player.
     */
    private static boolean collides(Vector2 position, Rectangle bounds, Block block) {
        if (Math.abs(position.x - block.getPosition().x) <= GS.BLOCK_WIDTH
                && Math.abs(position.y - block.getPosition().y) <= GS.BLOCK_HEIGHT) {
            return (Intersector.overlaps(bounds, block.getBounds()));
        }
        return false;
    }
}
